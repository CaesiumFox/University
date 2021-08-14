package io.github.caesiumfox.lab07.server;

import io.github.caesiumfox.lab07.common.KeyWord;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Класс для организации сессий.
 * Каждое выполнение команды -- одна сессия.
 * Разные команды даже от одного клиента --
 * разные сессии.
 */
public class SessionController extends Thread {
    private static class SessionState {
        private SocketAddress address;
        private String username;
        private ByteBuffer buffer;
        private boolean isOld;
    }

    private final Map<Long, SessionState> sessions;
    private MessageDigest digest;
    private Long sessionIdSequence;
    private final Object sessionIdSequenceMutex;
    private final ExecutorService commandCachedThreadPool;

    SessionController() {
        sessions = new HashMap<>();
        try {
            digest = MessageDigest.getInstance("MD2");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("MD2 is not supported");
        }
        sessionIdSequence = 1L;
        sessionIdSequenceMutex = new Object();
        commandCachedThreadPool = Executors.newCachedThreadPool();
    }

    @Override
    public void run() {
        try {
            while (Server.running) {
                ByteBuffer currentBuffer = ByteBuffer.allocate(NetworkManager.BUFFER_SIZE);
                SocketAddress client = Server.networkManager.receive(currentBuffer);
                KeyWord queryType = KeyWord.getKeyWord(currentBuffer.get());
                if (queryType == KeyWord.REGULAR_QUERY) {
                    String username = Server.readString(currentBuffer);
                    String password = Server.readString(currentBuffer);
                    boolean accepted = false;
                    try {
                        PreparedStatement getPass = Server.connection.prepareStatement(
                                "select pwhashstr, salt from users where username=?");
                        getPass.setString(1, username);
                        ResultSet saltResult = getPass.executeQuery();
                        if (saltResult.next()) {
                            String correctHash = saltResult.getString(1);
                            String salt = saltResult.getString(2);
                            if (correctHash.isEmpty()) {
                                accepted = password.isEmpty();
                            } else {
                                accepted = correctHash.equals(hashStr(password + salt));
                            }
                        }
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                        ByteBuffer response = ByteBuffer.allocate(NetworkManager.BUFFER_SIZE);
                        response.clear();
                        response.putLong(0L);
                        response.put(KeyWord.getCode(KeyWord.ERROR));
                        Server.writeString(response, e.getMessage());
                        response.flip();
                        Server.networkManager.send(response, client);
                        continue;
                    }
                    if (!accepted) {
                        ByteBuffer response = ByteBuffer.allocate(NetworkManager.BUFFER_SIZE);
                        response.clear();
                        response.putLong(0L);
                        response.put(KeyWord.getCode(KeyWord.LOGIN_INCORRECT));
                        response.flip();
                        Server.networkManager.send(response, client);
                        continue;
                    }
                    long sessionID = currentBuffer.getLong();
                    if (sessionID == 0) {
                        if (sessions.size() >= 1000) {
                            ByteBuffer response = ByteBuffer.allocate(1);
                            response.put(KeyWord.getCode(KeyWord.SERVER_OVERLOADED));
                            Server.networkManager.send(response, client);
                            continue;
                        }
                        sessionID = nextSessionIdSequence();
                        SessionState state = new SessionState();
                        state.address = client;
                        state.isOld = false;
                        state.username = username;
                        state.buffer = currentBuffer;
                        sessions.put(sessionID, state);
                        commandCachedThreadPool.submit(new CommandHandler(Server.database, sessionID));
                    } else if (!sessions.containsKey(sessionID)) {
                        ByteBuffer response = ByteBuffer.allocate(1);
                        response.put(KeyWord.getCode(KeyWord.INVALID_SESSION));
                        Server.networkManager.send(response, client);
                    } else {
                        waitForOldness(sessionID);
                        synchronized (sessions) {
                            sessions.get(sessionID).address = client;
                            sessions.get(sessionID).isOld = false;
                            sessions.get(sessionID).username = username;
                            sessions.get(sessionID).buffer = currentBuffer;
                        }
                    }
                } else { // SPECIAL_QUERY
                    KeyWord query = KeyWord.getKeyWord(currentBuffer.get());
                    switch (query) {
                        case REGISTER_USER: {
                            try {
                                String newUsername = Server.readString(currentBuffer);
                                String newPassword = Server.readString(currentBuffer);
                                Statement checkUsername = Server.connection.createStatement();
                                boolean isPresent;
                                ResultSet resultCheck = checkUsername.executeQuery(
                                        "select count(*) from users where username='"
                                                + newUsername + "'");
                                if (resultCheck.next()) {
                                    isPresent = resultCheck.getInt(1) > 0;
                                } else {
                                    throw new SQLException("Error checking the username");
                                }
                                if (isPresent) {
                                    ByteBuffer buffer = ByteBuffer.allocate(NetworkManager.BUFFER_SIZE);
                                    buffer.put(KeyWord.getCode(KeyWord.LOGIN_INCORRECT));
                                    Server.networkManager.send(buffer, client);
                                    break;
                                }
                                // User can be created
                                Statement createUser = Server.connection.createStatement();
                                String salt = generateSalt();
                                createUser.execute("insert into users values ('" +
                                        newUsername + "', '" + hashStr(newPassword + salt) + "', '" +
                                        salt + "');");

                                ByteBuffer buffer = ByteBuffer.allocate(NetworkManager.BUFFER_SIZE);
                                buffer.put(KeyWord.getCode(KeyWord.OK));
                                Server.networkManager.send(buffer, client);
                            } catch (SQLException e) {
                                ByteBuffer buffer = ByteBuffer.allocate(NetworkManager.BUFFER_SIZE);
                                buffer.put(KeyWord.getCode(KeyWord.ERROR));
                                Server.writeString(buffer, "Database error: " + e.getMessage());
                                Server.networkManager.send(buffer, client);
                            }
                            break;
                        }
                        default: {
                            ByteBuffer buffer = ByteBuffer.allocate(NetworkManager.BUFFER_SIZE);
                            buffer.put(KeyWord.getCode(KeyWord.ERROR));
                            Server.writeString(buffer, "Unexpected key word: " + query);
                            Server.networkManager.send(buffer, client);
                            break;
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Session controller interrupted.");
        } finally {
            commandCachedThreadPool.shutdown();
        }
    }

    private String hashStr(String str) {
        byte[] hash = digest.digest(str.getBytes());
        StringBuilder hashBuilder = new StringBuilder();
        for (byte b : hash) {
            hashBuilder.append(toHex((int) b & 0xf));
            hashBuilder.append(toHex(((int) b >> 4) & 0xf));
        }
        return hashBuilder.toString();
    }

    private static final String saltSymbols = " !#$%&()*+,-./0123456789:;<=>?@qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM[]^_`{|}~";
    private String generateSalt() {
        Random random = new Random();
        int length = 5 + random.nextInt(5); // 5 to 9
        StringBuilder salt = new StringBuilder();
        for (int i = 0; i < length; i++) {
            salt.append(saltSymbols.charAt(random.nextInt(saltSymbols.length())));
        }
        return salt.toString();
    }

    // Желательно получать не более одного раза,
    // так как устанавливается устаревшее состояние,
    // и буфер может перезаписаться.
    public ByteBuffer getSessionBuffer(long ID) {
        synchronized (sessions) {
            sessions.get(ID).isOld = true;
            return sessions.get(ID).buffer;
        }
    }

    public boolean getSessionOldnessState(long ID) {
        synchronized (sessions) {
            return sessions.get(ID).isOld;
        }
    }

    /**
     * Ожидает установки нового значения буфера;
     * рекомендуется выполнять перед
     * {@link #getSessionBuffer(long)}
     * @param ID идентификатор сессии
     */
    public void waitForStateUpdate(long ID) throws InterruptedException {
        while (getSessionOldnessState(ID)) {
            Thread.sleep(10);
        }
    }

    public void waitForOldness(long ID) throws InterruptedException {
        while (!getSessionOldnessState(ID)) {
            Thread.sleep(10);
        }
    }

    public String getSessionUsername(long ID) {
        synchronized (sessions) {
            return sessions.get(ID).username;
        }
    }

    public void freeSession(long ID) {
        synchronized (sessions) {
            sessions.remove(ID);
        }
    }

    public void sendResponse(ByteBuffer buffer, long sessionId) {
        Server.networkManager.send(buffer, sessions.get(sessionId).address);
    }

    private long nextSessionIdSequence() {
        synchronized (sessionIdSequenceMutex) {
            long result = sessionIdSequence;
            do {
                if (sessionIdSequence == Long.MAX_VALUE)
                    sessionIdSequence = 1L;
                else
                    sessionIdSequence++;
            } while (sessions.containsKey(sessionIdSequence));
            return result;
        }
    }

    private static char toHex(int b) {
        b &= 0xf;
        if (b <= 9) {
            return (char)((int)'0' + b);
        } else {
            return (char)((int)'a' + b - 10);
        }
    }
}
