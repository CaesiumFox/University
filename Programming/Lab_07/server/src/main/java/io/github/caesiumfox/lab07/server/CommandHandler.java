package io.github.caesiumfox.lab07.server;

import io.github.caesiumfox.lab07.common.KeyWord;
import io.github.caesiumfox.lab07.common.MovieComparator;
import io.github.caesiumfox.lab07.common.MutableDatabaseInfo;
import io.github.caesiumfox.lab07.common.entry.Movie;
import io.github.caesiumfox.lab07.common.entry.MpaaRating;
import io.github.caesiumfox.lab07.common.exceptions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

public class CommandHandler implements Runnable {
    private DatabaseManager database;
    private long session;

    public CommandHandler(DatabaseManager database, long session) {
        this.database = database;
        this.session = session;
    }

    @Override
    public void run() {
        ByteBuffer buffer = Server.sessionController.getSessionBuffer(session);
        KeyWord code = KeyWord.getKeyWord(buffer.get());
        switch (code) {
            case SOME_LEFT:    // is not supposed to be received from client
            case NOTHING_LEFT: // is not supposed to be received from client
            case CONTINUE:     // is handled separately
            case OK:           // is not supposed to be received from client
            case ERROR:        // is not supposed to be received from client
            case NO_OPERATION:
                break;
            case GET_INFO: {
                sendInfo(Server.networkManager, database.getMutableInfo());
                break;
            }
            case CHECK_PASSPORT_ID: {
                String passportID = Server.readString(buffer);
                sendBool(Server.networkManager, database.hasPassportID(passportID));
                break;
            }
            case CHECK_ID: {
                int id = buffer.getInt();
                sendBool(Server.networkManager, database.hasID(id));
                break;
            }
            case GET_ALL: {
                sendElements(Server.networkManager, database.getAllElements());
                break;
            }
            case INSERT: {
                try {
                    Movie.RawData rawData = new Movie.RawData();
                    rawData.getFromByteBuffer(buffer);
                    Movie movie = new Movie(rawData);
                    database.insert(movie);
                    sendOk(Server.networkManager);
                } catch (StringLengthLimitationException | CoordinatesOutOfRangeException |
                        NumberOutOfRangeException | RunOutOfIdsException |
                        PassportIdAlreadyExistsException e) {
                    sendError(Server.networkManager, e.getMessage());
                }
                break;
            }
            case INSERT_ID: {
                try {
                    Movie.RawData rawData = new Movie.RawData();
                    rawData.getFromByteBuffer(buffer);
                    Movie movie = new Movie(rawData);
                    database.insert(rawData.id, movie);
                    sendOk(Server.networkManager);
                } catch (StringLengthLimitationException | CoordinatesOutOfRangeException |
                        NumberOutOfRangeException | ElementIdAlreadyExistsException |
                        PassportIdAlreadyExistsException e) {
                    sendError(Server.networkManager, e.getMessage());
                }
                break;
            }
            case UPDATE: {
                try {
                    Movie.RawData rawData = new Movie.RawData();
                    rawData.getFromByteBuffer(buffer);
                    Movie movie = new Movie(rawData);
                    database.update(rawData.id, movie);
                    sendOk(Server.networkManager);
                } catch (StringLengthLimitationException | CoordinatesOutOfRangeException |
                        NumberOutOfRangeException | NoKeyInDatabaseException |
                        PassportIdAlreadyExistsException e) {
                    sendError(Server.networkManager, e.getMessage());
                }
                break;
            }
            case REMOVE_KEY: {
                try {
                    int id = buffer.getInt();
                    database.removeKey(id);
                    sendOk(Server.networkManager);
                } catch (NoKeyInDatabaseException | NumberOutOfRangeException e) {
                    sendError(Server.networkManager, e.getMessage());
                }
                break;
            }
            case REMOVE_LOWER: {
                try {
                    Movie.RawData rawData = new Movie.RawData();
                    rawData.getFromByteBuffer(buffer);
                    Movie movie = new Movie(rawData);
                    database.removeLower(movie);
                    sendOk(Server.networkManager);
                } catch (StringLengthLimitationException | CoordinatesOutOfRangeException |
                        NumberOutOfRangeException e) {
                    sendError(Server.networkManager, e.getMessage());
                }
                break;
            }
            case REMOVE_LOWER_KEY: {
                try {
                    int id = buffer.getInt();
                    database.removeLowerKey(id);
                    sendOk(Server.networkManager);
                } catch (NumberOutOfRangeException e) {
                    sendError(Server.networkManager, e.getMessage());
                }
                break;
            }
            case REMOVE_GREATER_KEY: {
                try {
                    int id = buffer.getInt();
                    database.removeGreaterKey(id);
                    sendOk(Server.networkManager);
                } catch (NumberOutOfRangeException e) {
                    sendError(Server.networkManager, e.getMessage());
                }
                break;
            }
            case CLEAR: {
                database.clear();
                sendOk(Server.networkManager);
                break;
            }
            case MIN_BY_MPAA: {
                try {
                    sendElement(Server.networkManager, database.minByMpaaRating());
                } catch (EmptyDatabaseException e) {
                    sendError(Server.networkManager, e.getMessage());
                }
                break;
            }
            case COUNT_GREATER_OSCARS: {
                long oscarsCount = buffer.getLong();
                sendInt(Server.networkManager, database.countGreaterThanOscarsCount(oscarsCount));
                break;
            }
            case FILTER_BY_MPAA: {
                MpaaRating rating = MpaaRating.fromOrdinal(buffer.getInt());
                List<Movie> list = database.filterByMpaaRating(rating)
                        .stream()
                        .sorted(new MovieComparator())
                        .collect(Collectors.toList());
                sendElements(Server.networkManager, list);
                break;
            }
            default:
                break;
        }
    }

    private void sendElements(List<Movie> movies) throws IOException {
        for (Movie movie : movies) {
            ByteBuffer buffer = ByteBuffer.allocate(NetworkManager.BUFFER_SIZE);
            buffer.clear();
            buffer.put(KeyWord.getCode(KeyWord.SOME_LEFT));
            movie.toRawData().putInByteBuffer(buffer);
            buffer.flip();
            Server.logger.info("Written movie (ID: " + movie.getID() + ")");
            Server.sessionController.sendResponse(buffer, session);
            buffer.clear();
            .receive(buffer);

            KeyWord code = KeyWord.getKeyWord(currentNM.byteBuffer.get());
            if (code != KeyWord.CONTINUE) {
                Server.logger.info("Read something, but not CONTINUE");
                break;
            } else {
                Server.logger.info("Read CONTINUE");
            }
        }

        currentNM.byteBuffer.clear();
        currentNM.byteBuffer.put(KeyWord.getCode(KeyWord.NOTHING_LEFT));
        currentNM.byteBuffer.flip();
        Server.logger.info("Written NOTHING_LEFT");
        currentNM.send();
    }

    private void sendElement(NetworkManager currentNM, Movie movie) throws IOException {
        currentNM.byteBuffer.clear();
        currentNM.byteBuffer.put(KeyWord.getCode(KeyWord.SOME_LEFT));
        movie.toRawData().putInByteBuffer(currentNM.byteBuffer);
        currentNM.byteBuffer.flip();
        Server.logger.info("Written single movie (ID: " + movie.getID() + ")");
        currentNM.send();
    }

    private void sendInfo(NetworkManager currentNM, MutableDatabaseInfo info) throws IOException {
        currentNM.byteBuffer.clear();
        currentNM.byteBuffer.put(KeyWord.getCode(KeyWord.SOME_LEFT));
        info.putInByteBuffer(currentNM.byteBuffer);
        currentNM.byteBuffer.flip();
        Server.logger.info("Written info");
        currentNM.send();
    }

    private void sendInt(NetworkManager currentNM, int value) throws IOException {
        currentNM.byteBuffer.clear();
        currentNM.byteBuffer.put(KeyWord.getCode(KeyWord.SOME_LEFT));
        currentNM.byteBuffer.putInt(value);
        currentNM.byteBuffer.flip();
        Server.logger.info("Written int: " + value);
        currentNM.send();
    }

    private void sendBool(NetworkManager currentNM, boolean value) throws IOException {
        currentNM.byteBuffer.clear();
        currentNM.byteBuffer.put(KeyWord.getCode(KeyWord.SOME_LEFT));
        currentNM.byteBuffer.put(value ? (byte)1 : (byte)0);
        currentNM.byteBuffer.flip();
        Server.logger.info("Written boolean: " + value);
        currentNM.send();
    }

    private void sendOk(NetworkManager currentNM) throws IOException {
        currentNM.byteBuffer.clear();
        currentNM.byteBuffer.put(KeyWord.getCode(KeyWord.OK));
        currentNM.byteBuffer.flip();
        Server.logger.info("Written OK");
        currentNM.send();
    }

    private void sendError(NetworkManager currentNM, String message) throws IOException {
        if(message == null)
            message = "NULL";
        currentNM.byteBuffer.clear();
        currentNM.byteBuffer.put(KeyWord.getCode(KeyWord.ERROR));
        currentNM.byteBuffer.putInt(message.length());
        for(int i = 0; i < message.length(); i++)
            currentNM.byteBuffer.putChar(message.charAt(i));
        currentNM.byteBuffer.flip();
        Server.logger.info("Written ERROR: " + message);
        currentNM.send();
    }
}
