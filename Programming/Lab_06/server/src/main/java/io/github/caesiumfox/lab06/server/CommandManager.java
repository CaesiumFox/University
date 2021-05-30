package io.github.caesiumfox.lab06.server;

import io.github.caesiumfox.lab06.common.KeyWord;
import io.github.caesiumfox.lab06.common.MovieComparator;
import io.github.caesiumfox.lab06.common.MutableDatabaseInfo;
import io.github.caesiumfox.lab06.common.entry.Movie;
import io.github.caesiumfox.lab06.common.entry.MpaaRating;
import io.github.caesiumfox.lab06.common.exceptions.*;
import sun.nio.ch.Net;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CommandManager {
    private DatabaseManager database;
    private boolean running;

    public CommandManager(DatabaseManager database) {
        Server.logger.info("Started command manager initialization");
        this.database = database;
        Server.logger.info("Initialized command manager");
    }

    public void run() {
        while(running) {
            try {
                handleCommands();
            } catch (Exception e) {
                System.out.println("Failed to run command. Error: " + e.getMessage());
                Server.logger.severe("Failed to run command. Error: " + e.getMessage());
            }
        }
    }

    private void handleCommands() throws Exception {
        NetworkManager.receive();
        KeyWord code = KeyWord.getKeyWord(NetworkManager.byteBuffer.get());
        switch (code) {
            case SOME_LEFT:    // is not supposed to be received from client
            case NOTHING_LEFT: // is not supposed to be received from client
            case CONTINUE:     // is handled separately
            case OK:           // is not supposed to be received from client
            case ERROR:        // is not supposed to be received from client
            case NO_OPERATION:
                break;
            case GET_INFO: {
                sendInfo(database.getMutableInfo());
                break;
            }
            case CHECK_PASSPORT_ID: {
                String passportID = readString(NetworkManager.byteBuffer);
                sendBool(database.hasPassportID(passportID));
                break;
            }
            case CHECK_ID: {
                int id = NetworkManager.byteBuffer.getInt();
                sendBool(database.hasID(id));
                break;
            }
            case GET_ALL: {
                sendElements(database.getAllElements());
                break;
            }
            case INSERT: {
                try {
                    Movie.RawData rawData = new Movie.RawData();
                    rawData.getFromByteBuffer(NetworkManager.byteBuffer);
                    Movie movie = new Movie(rawData);
                    database.insert(movie);
                    sendOk();
                } catch (StringLengthLimitationException | CoordinatesOutOfRangeException |
                        NumberOutOfRangeException | RunOutOfIdsException |
                        PassportIdAlreadyExistsException e) {
                    sendError(e.getMessage());
                }
                break;
            }
            case INSERT_ID: {
                try {
                    Movie.RawData rawData = new Movie.RawData();
                    rawData.getFromByteBuffer(NetworkManager.byteBuffer);
                    Movie movie = new Movie(rawData);
                    database.insert(rawData.id, movie);
                    sendOk();
                } catch (StringLengthLimitationException | CoordinatesOutOfRangeException |
                        NumberOutOfRangeException | ElementIdAlreadyExistsException |
                        PassportIdAlreadyExistsException e) {
                    sendError(e.getMessage());
                }
                break;
            }
            case UPDATE: {
                try {
                    Movie.RawData rawData = new Movie.RawData();
                    rawData.getFromByteBuffer(NetworkManager.byteBuffer);
                    Movie movie = new Movie(rawData);
                    database.update(rawData.id, movie);
                    sendOk();
                } catch (StringLengthLimitationException | CoordinatesOutOfRangeException |
                        NumberOutOfRangeException | NoKeyInDatabaseException |
                        PassportIdAlreadyExistsException e) {
                    sendError(e.getMessage());
                }
                break;
            }
            case REMOVE_KEY: {
                try {
                    int id = NetworkManager.byteBuffer.getInt();
                    database.removeKey(id);
                    sendOk();
                } catch (NoKeyInDatabaseException | NumberOutOfRangeException e) {
                    sendError(e.getMessage());
                }
                break;
            }
            case REMOVE_LOWER: {
                try {
                    Movie.RawData rawData = new Movie.RawData();
                    rawData.getFromByteBuffer(NetworkManager.byteBuffer);
                    Movie movie = new Movie(rawData);
                    database.removeLower(movie);
                    sendOk();
                } catch (StringLengthLimitationException | CoordinatesOutOfRangeException |
                        NumberOutOfRangeException e) {
                    sendError(e.getMessage());
                }
                break;
            }
            case REMOVE_LOWER_KEY: {
                try {
                    int id = NetworkManager.byteBuffer.getInt();
                    database.removeLowerKey(id);
                    sendOk();
                } catch (NumberOutOfRangeException e) {
                    sendError(e.getMessage());
                }
                break;
            }
            case REMOVE_GREATER_KEY: {
                try {
                    int id = NetworkManager.byteBuffer.getInt();
                    database.removeGreaterKey(id);
                    sendOk();
                } catch (NumberOutOfRangeException e) {
                    sendError(e.getMessage());
                }
                break;
            }
            case CLEAR: {
                database.clear();
                sendOk();
                break;
            }
            case MIN_BY_MPAA: {
                try {
                    sendElement(database.minByMpaaRating());
                } catch (EmptyDatabaseException e) {
                    sendError(e.getMessage());
                }
                break;
            }
            case COUNT_GREATER_OSCARS: {
                long oscarsCount = NetworkManager.byteBuffer.getLong();
                sendInt(database.countGreaterThanOscarsCount(oscarsCount));
                break;
            }
            case FILTER_BY_MPAA: {
                MpaaRating rating = MpaaRating.fromOrdinal(NetworkManager.byteBuffer.getInt());
                List<Movie> list = database.filterByMpaaRating(rating)
                        .stream()
                        .sorted(new MovieComparator())
                        .collect(Collectors.toList());
                sendElements(list);
                break;
            }
        }
    }

    private String readString(ByteBuffer buffer) {
        int size = buffer.getInt();
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < size; i++)
            builder.append(buffer.getChar());
        return builder.toString();
    }

    private void sendElements(List<Movie> movies) throws IOException {
        for (Movie movie : movies) {
            NetworkManager.byteBuffer.clear();
            NetworkManager.byteBuffer.put(KeyWord.getCode(KeyWord.SOME_LEFT));
            movie.toRawData().putInByteBuffer(NetworkManager.byteBuffer);
            NetworkManager.byteBuffer.flip();
            Server.logger.info("Written movie (ID: " + movie.getID() + ")");
            NetworkManager.send();

            NetworkManager.receive();

            KeyWord code = KeyWord.getKeyWord(NetworkManager.byteBuffer.get());
            if (code != KeyWord.CONTINUE) {
                Server.logger.info("Read something, but not CONTINUE");
                break;
            } else {
                Server.logger.info("Written CONTINUE");
            }
        }

        NetworkManager.byteBuffer.clear();
        NetworkManager.byteBuffer.put(KeyWord.getCode(KeyWord.NOTHING_LEFT));
        NetworkManager.byteBuffer.flip();
        Server.logger.info("Written NOTHING_LEFT");
        NetworkManager.send();
    }

    private void sendElement(Movie movie) throws IOException {
        NetworkManager.byteBuffer.clear();
        NetworkManager.byteBuffer.put(KeyWord.getCode(KeyWord.SOME_LEFT));
        movie.toRawData().putInByteBuffer(NetworkManager.byteBuffer);
        NetworkManager.byteBuffer.flip();
        Server.logger.info("Written single movie (ID: " + movie.getID() + ")");
        NetworkManager.send();
    }

    private void sendInfo(MutableDatabaseInfo info) throws IOException {
        NetworkManager.byteBuffer.clear();
        NetworkManager.byteBuffer.put(KeyWord.getCode(KeyWord.SOME_LEFT));
        info.putInByteBuffer(NetworkManager.byteBuffer);
        NetworkManager.byteBuffer.flip();
        Server.logger.info("Written info");
        NetworkManager.send();
    }

    private void sendInt(int value) throws IOException {
        NetworkManager.byteBuffer.clear();
        NetworkManager.byteBuffer.put(KeyWord.getCode(KeyWord.SOME_LEFT));
        NetworkManager.byteBuffer.putInt(value);
        NetworkManager.byteBuffer.flip();
        Server.logger.info("Written int: " + value);
        NetworkManager.send();
    }

    private void sendBool(boolean value) throws IOException {
        NetworkManager.byteBuffer.clear();
        NetworkManager.byteBuffer.put(KeyWord.getCode(KeyWord.SOME_LEFT));
        NetworkManager.byteBuffer.put(value ? (byte)1 : (byte)0);
        NetworkManager.byteBuffer.flip();
        Server.logger.info("Written boolean: " + value);
        NetworkManager.send();
    }

    private void sendOk() throws IOException {
        NetworkManager.byteBuffer.clear();
        NetworkManager.byteBuffer.put(KeyWord.getCode(KeyWord.OK));
        NetworkManager.byteBuffer.flip();
        Server.logger.info("Written OK");
        NetworkManager.send();
    }

    private void sendError(String message) throws IOException {
        NetworkManager.byteBuffer.clear();
        NetworkManager.byteBuffer.put(KeyWord.getCode(KeyWord.ERROR));
        NetworkManager.byteBuffer.putInt(message.length());
        for(int i = 0; i < message.length(); i++)
            NetworkManager.byteBuffer.putChar(message.charAt(i));
        NetworkManager.byteBuffer.flip();
        Server.logger.info("Written ERROR: " + message);
        NetworkManager.send();
    }
}
