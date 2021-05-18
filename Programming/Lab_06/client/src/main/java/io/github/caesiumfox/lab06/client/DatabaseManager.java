package io.github.caesiumfox.lab06.client;

import io.github.caesiumfox.lab06.common.Database;
import io.github.caesiumfox.lab06.common.KeyWord;
import io.github.caesiumfox.lab06.common.MutableDatabaseInfo;
import io.github.caesiumfox.lab06.common.entry.*;
import io.github.caesiumfox.lab06.common.exceptions.*;

import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DatabaseManager implements Database {
    MutableDatabaseInfo databaseInfo;

    public DatabaseManager() throws IOException {
        NetworkManager.byteBuffer.clear();
        NetworkManager.byteBuffer.put(KeyWord.getCode(KeyWord.GET_INFO));
        NetworkManager.byteBuffer.flip();

        NetworkManager.send();
        NetworkManager.receive();

        NetworkManager.byteBuffer.get(); // for SOME_LEFT
        databaseInfo.getFromByteBuffer(NetworkManager.byteBuffer);
    }

    public void updateDatabaseInfo() throws IOException {
        NetworkManager.byteBuffer.clear();
        NetworkManager.byteBuffer.put(KeyWord.getCode(KeyWord.GET_INFO));
        NetworkManager.byteBuffer.flip();

        NetworkManager.send();
        NetworkManager.receive();

        NetworkManager.byteBuffer.get(); // for SOME_LEFT
        databaseInfo.getFromByteBuffer(NetworkManager.byteBuffer);
    }

    @Override
    public boolean hasPassportID(String passportId) {
        NetworkManager.byteBuffer.clear();
        NetworkManager.byteBuffer.put(KeyWord.getCode(KeyWord.CHECK_PASSPORT_ID));
        NetworkManager.byteBuffer.putInt(passportId.length());
        for(int i = 0; i < passportId.length(); i++) {
            NetworkManager.byteBuffer.putChar(passportId.charAt(i));
        }
        NetworkManager.byteBuffer.flip();

        try {
            NetworkManager.send();
            NetworkManager.receive();
        } catch (IOException e) {
            return false;
        }

        NetworkManager.byteBuffer.get(); // for SOME_LEFT
        return NetworkManager.byteBuffer.get() != 0;
    }

    @Override
    public String getInputFile() {
        return databaseInfo.getInputFile();
    }

    @Override
    public Date getCreationDate() {
        return databaseInfo.getCreationDate();
    }

    @Override
    public boolean hasID(Integer id) {
        NetworkManager.byteBuffer.clear();
        NetworkManager.byteBuffer.put(KeyWord.getCode(KeyWord.CHECK_PASSPORT_ID));
        NetworkManager.byteBuffer.putInt(id);
        NetworkManager.byteBuffer.flip();

        try {
            NetworkManager.send();
            NetworkManager.receive();
        } catch (IOException e) {
            return false;
        }

        NetworkManager.byteBuffer.get(); // for SOME_LEFT
        return NetworkManager.byteBuffer.get() != 0;
    }

    @Override
    public boolean hasRanOutOfIDs() {
        return databaseInfo.getMaxID() == Integer.MAX_VALUE;
    }

    private void handleDichotomousResponse() throws IOException {
            NetworkManager.send();
            NetworkManager.receive();

            KeyWord response = KeyWord.getKeyWord(NetworkManager.byteBuffer.get());
            if(response == KeyWord.ERROR) {
                StringBuilder messageBuilder = new StringBuilder();
                int messageLen = NetworkManager.byteBuffer.getInt();
                for (int i = 0; i < messageLen; i++) {
                    messageBuilder.append(NetworkManager.byteBuffer.getChar());
                }
                System.out.println("Server returned an error:");
                System.out.println(messageBuilder.toString());
            }
    }

    @Override
    public void info(PrintStream output) {
        output.println("--- Database info ---");
        output.print("  Input File:      ");
        output.println(databaseInfo.getInputFile().length() == 0 ? "<N/A>" : databaseInfo.getInputFile());
        output.print("  Creation Date:   ");
        output.println(new SimpleDateFormat(Client.dateFormat).format(databaseInfo.getCreationDate()));
        output.print("  Max ID:          ");
        output.println(databaseInfo.getMaxID());
        output.print("  N/O Elements:    ");
        output.println(databaseInfo.getNumberOfElements());

        output.println("  Collection type: LinkedHashMap");
        output.println("  Fields:");
        output.println("    ID: [1 - 2147483647]");
        output.println("    Name: Not empty string");
        output.println("    Coordinates:");
        output.format("      X: [%f, %f]\n", Coordinates.minX, Coordinates.maxX);
        output.format("      Y: [%f, %f]\n", Coordinates.minY, Coordinates.maxY);
        output.format("    Creation Date: %s\n", Client.dateFormat);
        output.println("    Oscars Count: [1 - 9223372036854775807]");
        output.format("    Genre: %s\n", MovieGenre.listConstants());
        output.format("    MPAA Rating: %s\n", MpaaRating.listConstants());
        output.println("    Director (may be null):");
        output.println("      Name: Not empty string");
        output.format("      Passport ID (may be null): string with %d to %d characters\n",
                Person.passportIDMinLen, Person.passportIDMaxLen);
        output.format("      Hair Color: %s\n", Color.listConstants());
    }

    @Override
    public void show(PrintStream output) throws IOException {
        NetworkManager.byteBuffer.clear();
        NetworkManager.byteBuffer.put(KeyWord.getCode(KeyWord.GET_ALL));
        NetworkManager.byteBuffer.flip();

            boolean isEmpty = true;
            int corruptedElements = 0;
            while (true) {
                NetworkManager.send();
                NetworkManager.receive();

                KeyWord response = KeyWord.getKeyWord(NetworkManager.byteBuffer.get());
                if (response == KeyWord.SOME_LEFT) {
                    isEmpty = false;
                    Movie.RawData entry = new Movie.RawData();
                    entry.getFromByteBuffer(NetworkManager.byteBuffer);
                    try {
                        output.println(new Movie(entry).toString());
                    } catch (CoordinatesOutOfRangeException | NumberOutOfRangeException |
                            StringLengthLimitationException e) {
                        corruptedElements++;
                    }

                    NetworkManager.byteBuffer.clear();
                    NetworkManager.byteBuffer.put(KeyWord.getCode(KeyWord.CONTINUE));
                    NetworkManager.byteBuffer.flip();
                    // then go send & receive the next iteration
                } else {
                    break;
                }
            }
            if (isEmpty) {
                output.println("  There are No Elements");
            }
            if(corruptedElements > 0) {
                output.println("  There are " + corruptedElements + " corrupted elements");
            }
    }

    @Override
    public void insert(Movie movie) throws RunOutOfIdsException,
            PassportIdAlreadyExistsException, NumberOutOfRangeException,
            IOException {
        NetworkManager.byteBuffer.clear();
        NetworkManager.byteBuffer.put(KeyWord.getCode(KeyWord.INSERT));
        movie.toRawData().putInByteBuffer(NetworkManager.byteBuffer);
        NetworkManager.byteBuffer.flip();

        handleDichotomousResponse();
    }

    @Override
    public void insert(Integer id, Movie movie) throws
            ElementIdAlreadyExistsException,
            PassportIdAlreadyExistsException,
            NumberOutOfRangeException,
            IOException {
        NetworkManager.byteBuffer.clear();
        NetworkManager.byteBuffer.put(KeyWord.getCode(KeyWord.INSERT_ID));
        movie.setID(id);
        movie.toRawData().putInByteBuffer(NetworkManager.byteBuffer);
        NetworkManager.byteBuffer.flip();

        handleDichotomousResponse();
    }

    @Override
    public void update(Integer id, Movie movie) throws
            NoKeyInDatabaseException,
            PassportIdAlreadyExistsException,
            NumberOutOfRangeException,
            IOException {
        NetworkManager.byteBuffer.clear();
        NetworkManager.byteBuffer.put(KeyWord.getCode(KeyWord.UPDATE));
        movie.setID(id);
        movie.toRawData().putInByteBuffer(NetworkManager.byteBuffer);
        NetworkManager.byteBuffer.flip();

        handleDichotomousResponse();
    }

    @Override
    public void removeKey(Integer id) throws
            NoKeyInDatabaseException,
            NumberOutOfRangeException,
            IOException {
        NetworkManager.byteBuffer.clear();
        NetworkManager.byteBuffer.put(KeyWord.getCode(KeyWord.REMOVE_LOWER));
        NetworkManager.byteBuffer.putInt(id);
        NetworkManager.byteBuffer.flip();

        handleDichotomousResponse();
    }

    @Override
    public void clear() throws IOException {
        NetworkManager.byteBuffer.clear();
        NetworkManager.byteBuffer.put(KeyWord.getCode(KeyWord.CLEAR));
        NetworkManager.byteBuffer.flip();

        handleDichotomousResponse();
    }

    @Override
    public void removeLower(Movie movie) throws Exception {
        NetworkManager.byteBuffer.clear();
        NetworkManager.byteBuffer.put(KeyWord.getCode(KeyWord.REMOVE_LOWER));
        movie.toRawData().putInByteBuffer(NetworkManager.byteBuffer);
        NetworkManager.byteBuffer.flip();

        handleDichotomousResponse();
    }

    @Override
    public void removeGreaterKey(Integer id) throws
            NumberOutOfRangeException, IOException {
        NetworkManager.byteBuffer.clear();
        NetworkManager.byteBuffer.put(KeyWord.getCode(KeyWord.REMOVE_GREATER_KEY));
        NetworkManager.byteBuffer.putInt(id);
        NetworkManager.byteBuffer.flip();

        handleDichotomousResponse();
    }

    @Override
    public void removeLowerKey(Integer id) throws
            NumberOutOfRangeException, IOException {
        NetworkManager.byteBuffer.clear();
        NetworkManager.byteBuffer.put(KeyWord.getCode(KeyWord.REMOVE_LOWER_KEY));
        NetworkManager.byteBuffer.putInt(id);
        NetworkManager.byteBuffer.flip();

        handleDichotomousResponse();
    }

    @Override
    public Movie minByMpaaRating() throws EmptyDatabaseException, IOException {
        NetworkManager.byteBuffer.clear();
        NetworkManager.byteBuffer.put(KeyWord.getCode(KeyWord.MIN_BY_MPAA));
        NetworkManager.byteBuffer.flip();

        NetworkManager.send();
        NetworkManager.receive();

        KeyWord response = KeyWord.getKeyWord(NetworkManager.byteBuffer.get());
        if (response == KeyWord.SOME_LEFT) {
            Movie.RawData entry = new Movie.RawData();
            entry.getFromByteBuffer(NetworkManager.byteBuffer);
            try {
                return new Movie(entry);
            } catch (CoordinatesOutOfRangeException | NumberOutOfRangeException |
                    StringLengthLimitationException e) {
                System.out.println("Corrupted element returned");
            }
        } else {
            throw new EmptyDatabaseException();
        }
    }

    @Override
    public int countGreaterThanOscarsCount(long oscarsCount) throws IOException {
        NetworkManager.byteBuffer.clear();
        NetworkManager.byteBuffer.put(KeyWord.getCode(KeyWord.COUNT_GREATER_OSCARS));
        NetworkManager.byteBuffer.flip();

        NetworkManager.send();
        NetworkManager.receive();

        NetworkManager.byteBuffer.get(); // for SOME_LEFT
        return NetworkManager.byteBuffer.getInt();
    }

    @Override
    public Set<Movie> filterByMpaaRating(MpaaRating rating) throws IOException {
        NetworkManager.byteBuffer.clear();
        NetworkManager.byteBuffer.put(KeyWord.getCode(KeyWord.FILTER_BY_MPAA));
        NetworkManager.byteBuffer.putInt(rating.ordinal());
        NetworkManager.byteBuffer.flip();

        Set<Movie> result = new HashSet<>();

        int corruptedElements = 0;
        while (true) {
            NetworkManager.send();
            NetworkManager.receive();

            KeyWord response = KeyWord.getKeyWord(NetworkManager.byteBuffer.get());
            if(response == KeyWord.SOME_LEFT) {
                Movie.RawData entry = new Movie.RawData();
                entry.getFromByteBuffer(NetworkManager.byteBuffer);
                try {
                    result.add(new Movie(entry));
                } catch (CoordinatesOutOfRangeException | NumberOutOfRangeException |
                        StringLengthLimitationException e) {
                    corruptedElements++;
                }

                NetworkManager.byteBuffer.clear();
                NetworkManager.byteBuffer.put(KeyWord.getCode(KeyWord.CONTINUE));
                NetworkManager.byteBuffer.flip();
                // then go send & receive the next iteration
            } else {
                break;
            }
        }
        if(corruptedElements > 0) {
            System.out.println("  There are " + corruptedElements + " corrupted elements");
        }
    }
}
