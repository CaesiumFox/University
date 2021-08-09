package io.github.caesiumfox.lab07.server;

import io.github.caesiumfox.lab07.common.KeyWord;

import java.io.IOException;
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

public class SessionController extends Thread {
    private static class SessionState {
        private SocketAddress address;
        private String username;
        private ByteBuffer buffer;
        private boolean isOld;
    }

    private Map<Long, SessionState> sessions;
    private MessageDigest digest;
    private Long sessionIdSequence;
    private ExecutorService commandCachedThreadPool;

    SessionController() {
        sessions = new HashMap<Long, SessionState>();
        try {
            digest = MessageDigest.getInstance("MD2");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("MD2 is not supported");
        }
        sessionIdSequence = 1L;
        commandCachedThreadPool = Executors.newCachedThreadPool();
    }

    @Override
    public void run() {
        try {
            while (Server.running) {
                ByteBuffer currentBuffer = ByteBuffer.allocate(NetworkManager.BUFFER_SIZE);
                SocketAddress client = Server.networkManager.receive(currentBuffer);
                String username = Server.readString(currentBuffer);
                String password = Server.readString(currentBuffer);
                boolean accepted = false;
                while (true) {
                    try {
                        PreparedStatement getPass = Server.connection.prepareStatement(
                                "select pwhashstr, salt from users where username=?");
                        getPass.setString(1, username);
                        ResultSet saltResult = getPass.executeQuery();
                        String correctHash = saltResult.getString(1);
                        String salt = saltResult.getString(2);
                        if (correctHash.isEmpty()) {
                            accepted = password.isEmpty();
                        } else {
                            String passPlusSalt = password + salt;
                            byte[] hash = digest.digest(passPlusSalt.getBytes());
                            StringBuilder hashBuilder = new StringBuilder();
                            for (byte b : hash) {
                                hashBuilder.append(toHex((int) b & 0xf));
                                hashBuilder.append(toHex(((int) b >> 4) & 0xf));
                            }
                            accepted = correctHash.equals(hashBuilder.toString());
                        }
                        break;
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                }
                if (!accepted) {
                    ByteBuffer response = ByteBuffer.allocate(1);
                    response.put(KeyWord.getCode(KeyWord.LOGIN_INCORRECT));
                    Server.networkManager.send(response, client);
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
                } else if (!sessions.containsKey(sessionID)) {
                    ByteBuffer response = ByteBuffer.allocate(1);
                    response.put(KeyWord.getCode(KeyWord.INVALID_SESSION));
                    Server.networkManager.send(response, client);
                    continue;
                } else {
                    waitForStateUpdate(sessionID);
                    synchronized (sessions) {
                        sessions.get(sessionID).address = client;
                        sessions.get(sessionID).isOld = false;
                        sessions.get(sessionID).username = username;
                        sessions.get(sessionID).buffer = currentBuffer;
                    }
                }
                commandCachedThreadPool.submit(new CommandHandler(Server.database, sessionID));

            }
        } catch (InterruptedException e) {
            System.out.println("Session controller interrupted.");
        } finally {
            commandCachedThreadPool.shutdown();
        }
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

    public void waitForStateUpdate(long ID) throws InterruptedException {
        while (getSessionOldnessState(ID)) {
            Thread.sleep(10);
        }
    }

    public String getSessionUsername(long ID) {
        synchronized (sessions) {
            return sessions.get(ID).username;
        }
    }

    public SocketAddress getSessionAddress(long ID) {
        synchronized (sessions) {
            return sessions.get(ID).address;
        }
    }

    public void freeSession(long ID) {
        synchronized (sessions) {
            sessions.remove(ID);
        }
    }

    public void sendResponse(ByteBuffer buffer, long sessionId) throws IOException {
        Server.networkManager.send(buffer, sessions.get(sessionId).address);
    }

    private long nextSessionIdSequence() {
        synchronized (sessionIdSequence) {
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
