package io.github.caesiumfox.lab07.server;

import io.github.caesiumfox.lab07.common.KeyWord;
import io.github.caesiumfox.lab07.common.MovieComparator;
import io.github.caesiumfox.lab07.common.MutableDatabaseInfo;
import io.github.caesiumfox.lab07.common.entry.Movie;
import io.github.caesiumfox.lab07.common.entry.MpaaRating;
import io.github.caesiumfox.lab07.common.exceptions.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static io.github.caesiumfox.lab07.common.KeyWord.NO_OPERATION;

public class CommandHandler implements Runnable {
    private final DatabaseManager database;
    private final long session;

    public CommandHandler(DatabaseManager database, long session) {
        this.database = database;
        this.session = session;
    }

    @Override
    public void run() {
        ByteBuffer buffer = Server.sessionController.getSessionBuffer(session);
        KeyWord code = KeyWord.getKeyWord(buffer.get());
        switch (code) {
            case NO_OPERATION: {
                try {
                    sendKeyWord(NO_OPERATION);
                } catch (IOException e) {
                    Server.logger.severe("Failed to send message");
                }
                break;
            }
            case GET_INFO: {
                try {
                    sendInfo(database.getMutableInfo());
                } catch (IOException e) {
                    Server.logger.severe("Failed to send message");
                }
                break;
            }
            case CHECK_PASSPORT_ID: {
                try {
                    String passportID = Server.readString(buffer);
                    sendBool(database.hasPassportID(passportID));
                } catch (IOException e) {
                    Server.logger.severe("Failed to send message");
                }
                break;
            }
            case CHECK_ID: {
                try {
                    int id = buffer.getInt();
                    sendBool(database.hasID(id));
                } catch (IOException e) {
                    Server.logger.severe("Failed to send message");
                }
                break;
            }
            case GET_ALL: {
                try {
                    try {
                        sendElements(database.getAllElements());
                    } catch (InterruptedException e) {
                        sendError("Server interrupted");
                    }
                } catch (IOException e) {
                    Server.logger.severe("Failed to send message");
                }
                break;
            }
            case INSERT: {
                try {
                    try {
                        Movie.RawData rawData = new Movie.RawData();
                        rawData.getFromByteBuffer(buffer);
                        Movie movie = new Movie(rawData);
                        database.insert(movie, Server.sessionController.getSessionUsername(session));
                        sendOk();
                    } catch (StringLengthLimitationException | CoordinatesOutOfRangeException |
                            NumberOutOfRangeException | RunOutOfIdsException |
                            PassportIdAlreadyExistsException e) {
                        sendError(e.getMessage());
                    } catch (SQLException e) {
                        sendError("Database error: " + e.getMessage());
                    }
                } catch (IOException e) {
                    Server.logger.severe("Failed to send message");
                }
                break;
            }
            case INSERT_ID: {
                try {
                    try {
                        Movie.RawData rawData = new Movie.RawData();
                        rawData.getFromByteBuffer(buffer);
                        Movie movie = new Movie(rawData);
                        database.insert(rawData.id, movie, Server.sessionController.getSessionUsername(session));
                        sendOk();
                    } catch (StringLengthLimitationException | CoordinatesOutOfRangeException |
                            NumberOutOfRangeException | ElementIdAlreadyExistsException |
                            PassportIdAlreadyExistsException e) {
                        sendError(e.getMessage());
                    } catch (SQLException e) {
                        sendError("Database error: " + e.getMessage());
                    }
                } catch (IOException e) {
                    Server.logger.severe("Failed to send message");
                }
                break;
            }
            case UPDATE: {
                try {
                    try {
                        Movie.RawData rawData = new Movie.RawData();
                        rawData.getFromByteBuffer(buffer);
                        Movie movie = new Movie(rawData);
                        database.update(rawData.id, movie, Server.sessionController.getSessionUsername(session));
                        sendOk();
                    } catch (StringLengthLimitationException | CoordinatesOutOfRangeException |
                            NumberOutOfRangeException | NoKeyInDatabaseException |
                            PassportIdAlreadyExistsException | NotAnOwnerException e) {
                        sendError(e.getMessage());
                    } catch (SQLException e) {
                        sendError("Database error: " + e.getMessage());
                    }
                } catch (IOException e) {
                    Server.logger.severe("Failed to send message");
                }
                break;
            }
            case REMOVE_KEY: {
                try {
                    try {
                        int id = buffer.getInt();
                        database.removeKey(id, Server.sessionController.getSessionUsername(session));
                        sendOk();
                    } catch (NoKeyInDatabaseException | NumberOutOfRangeException |
                            NotAnOwnerException e) {
                        sendError(e.getMessage());
                    } catch (SQLException e) {
                        sendError("Database error: " + e.getMessage());
                    }
                } catch (IOException e) {
                    Server.logger.severe("Failed to send message");
                }
                break;
            }
            case REMOVE_LOWER: {
                try {
                    try {
                        Movie.RawData rawData = new Movie.RawData();
                        rawData.getFromByteBuffer(buffer);
                        Movie movie = new Movie(rawData);
                        database.removeLower(movie, Server.sessionController.getSessionUsername(session));
                        sendOk();
                    } catch (StringLengthLimitationException | CoordinatesOutOfRangeException |
                            NumberOutOfRangeException e) {
                        sendError(e.getMessage());
                    } catch (SQLException e) {
                        sendError("Database error: " + e.getMessage());
                    }
                } catch (IOException e) {
                    Server.logger.severe("Failed to send message");
                }
                break;
            }
            case REMOVE_LOWER_KEY: {
                try {
                    try {
                        int id = buffer.getInt();
                        database.removeLowerKey(id, Server.sessionController.getSessionUsername(session));
                        sendOk();
                    } catch (NumberOutOfRangeException e) {
                        sendError(e.getMessage());
                    } catch (SQLException e) {
                        sendError("Database error: " + e.getMessage());
                    }
                } catch (IOException e) {
                    Server.logger.severe("Failed to send message");
                }
                break;
            }
            case REMOVE_GREATER_KEY: {
                try {
                    try {
                        int id = buffer.getInt();
                        database.removeGreaterKey(id, Server.sessionController.getSessionUsername(session));
                        sendOk();
                    } catch (NumberOutOfRangeException e) {
                        sendError(e.getMessage());
                    } catch (SQLException e) {
                        sendError("Database error: " + e.getMessage());
                    }
                } catch (IOException e) {
                    Server.logger.severe("Failed to send message");
                }
                break;
            }
            case CLEAR: {
                try {
                    try {
                        database.clear(Server.sessionController.getSessionUsername(session));
                        sendOk();
                    } catch (SQLException e) {
                        sendError("Database error: " + e.getMessage());
                    }
                } catch (IOException e) {
                    Server.logger.severe("Failed to send message");
                }
                break;
            }
            case MIN_BY_MPAA: {
                try {
                    try {
                        sendElement(database.minByMpaaRating());
                    } catch (EmptyDatabaseException e) {
                        sendError(e.getMessage());
                    }
                } catch (IOException e) {
                    Server.logger.severe("Failed to send message");
                }
                break;
            }
            case COUNT_GREATER_OSCARS: {
                try {
                    long oscarsCount = buffer.getLong();
                    sendInt(database.countGreaterThanOscarsCount(oscarsCount));
                } catch (IOException e) {
                    Server.logger.severe("Failed to send message");
                }
                break;
            }
            case FILTER_BY_MPAA: {
                try {
                    try {
                        MpaaRating rating = MpaaRating.fromOrdinal(buffer.getInt());
                        List<Movie> list = database.filterByMpaaRating(rating)
                                .stream()
                                .sorted(new MovieComparator())
                                .collect(Collectors.toList());
                        sendElements(list);
                    } catch (InterruptedException e) {
                        sendError("Server interrupted");
                    }
                } catch (IOException e) {
                    Server.logger.severe("Failed to send message");
                }
                break;
            }
            case LOGIN_CHECK: {
                try {
                    sendOk();
                } catch (IOException e) {
                    Server.logger.severe("Failed to send message");
                }
                break;
            }
            default: {
                try {
                    sendError("Unexpected command: " + code.name());
                } catch (IOException e) {
                    Server.logger.severe("Failed to send message");
                }
                break;
            }
        }
        Server.sessionController.freeSession(session);
    }

    private void sendElements(List<Movie> movies) throws IOException, InterruptedException {
        for (Movie movie : movies) {
            ByteBuffer buffer = prepareBuffer();
            buffer.put(KeyWord.getCode(KeyWord.SOME_LEFT));
            movie.toRawData().putInByteBuffer(buffer);
            buffer.flip();
            Server.logger.info("Written movie (ID: " + movie.getID() + ")");
            Server.sessionController.sendResponse(buffer, session);
            buffer.clear();
            Server.sessionController.waitForStateUpdate(session);
            buffer = Server.sessionController.getSessionBuffer(session);

            KeyWord code = KeyWord.getKeyWord(buffer.get());
            if (code != KeyWord.CONTINUE) {
                Server.logger.info("Have read something, but not CONTINUE");
                sendError("Expected CONTINUE");
                break;
            } else {
                Server.logger.info("Have read CONTINUE");
            }
        }

        ByteBuffer buffer = prepareBuffer();
        buffer.put(KeyWord.getCode(KeyWord.NOTHING_LEFT));
        buffer.flip();
        Server.logger.info("Written NOTHING_LEFT");
        Server.sessionController.sendResponse(buffer, session);
    }

    private void sendElement(Movie movie) throws IOException {
        ByteBuffer buffer = prepareBuffer();
        buffer.put(KeyWord.getCode(KeyWord.SOME_LEFT));
        movie.toRawData().putInByteBuffer(buffer);
        buffer.flip();
        Server.logger.info("Written single movie (ID: " + movie.getID() + ")");
        Server.sessionController.sendResponse(buffer, session);
    }

    private void sendInfo(MutableDatabaseInfo info) throws IOException {
        ByteBuffer buffer = prepareBuffer();
        buffer.put(KeyWord.getCode(KeyWord.SOME_LEFT));
        info.putInByteBuffer(buffer);
        buffer.flip();
        Server.logger.info("Written info");
        Server.sessionController.sendResponse(buffer, session);
    }

    private void sendInt(int value) throws IOException {
        ByteBuffer buffer = prepareBuffer();
        buffer.put(KeyWord.getCode(KeyWord.SOME_LEFT));
        buffer.putInt(value);
        buffer.flip();
        Server.logger.info("Written int: " + value);
        Server.sessionController.sendResponse(buffer, session);
    }

    private void sendBool(boolean value) throws IOException {
        ByteBuffer buffer = prepareBuffer();
        buffer.put(KeyWord.getCode(KeyWord.SOME_LEFT));
        buffer.put(value ? (byte)1 : (byte)0);
        buffer.flip();
        Server.logger.info("Written boolean: " + value);
        Server.sessionController.sendResponse(buffer, session);
    }

    private void sendOk() throws IOException {
        sendKeyWord(KeyWord.OK);
    }

    private void sendKeyWord(KeyWord word) throws IOException {
        ByteBuffer buffer = prepareBuffer();
        buffer.put(KeyWord.getCode(word));
        buffer.flip();
        Server.logger.info("Written OK");
        Server.sessionController.sendResponse(buffer, session);
    }

    private void sendError(String message) throws IOException {
        ByteBuffer buffer = prepareBuffer();
        if(message == null)
            message = "";
        buffer.put(KeyWord.getCode(KeyWord.ERROR));
        Server.writeString(buffer, message);
        buffer.flip();
        Server.logger.info("Written ERROR: " + message);
        Server.sessionController.sendResponse(buffer, session);
    }

    private ByteBuffer prepareBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(NetworkManager.BUFFER_SIZE);
        buffer.clear();
        buffer.putLong(session);
        return buffer;
    }
}
