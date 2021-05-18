package io.github.caesiumfox.lab06.client;

import io.github.caesiumfox.lab06.common.Database;
import io.github.caesiumfox.lab06.common.KeyWord;
import io.github.caesiumfox.lab06.common.MutableDatabaseInfo;
import io.github.caesiumfox.lab06.common.entry.Movie;
import io.github.caesiumfox.lab06.common.entry.MpaaRating;
import io.github.caesiumfox.lab06.common.exceptions.*;
import sun.nio.ch.Net;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
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

    @Override
    public void info(PrintStream output) {

    }

    @Override
    public void show(PrintStream output) {

    }

    @Override
    public void insert(Movie movie) throws RunOutOfIdsException,
            PassportIdAlreadyExistsException, NumberOutOfRangeException {

    }

    @Override
    public void insert(Integer id, Movie movie) throws
            ElementIdAlreadyExistsException,
            PassportIdAlreadyExistsException,
            NumberOutOfRangeException {

    }

    @Override
    public void update(Integer id, Movie movie) throws
            NoKeyInDatabaseException,
            PassportIdAlreadyExistsException,
            NumberOutOfRangeException {

    }

    @Override
    public void removeKey(Integer id) throws
            NoKeyInDatabaseException,
            NumberOutOfRangeException {

    }

    @Override
    public void clear() {

    }

    @Override
    public void removeLower(Movie movie) {

    }

    @Override
    public void removeGreaterKey(Integer id) throws NumberOutOfRangeException {

    }

    @Override
    public void removeLowerKey(Integer id) throws NumberOutOfRangeException {

    }

    @Override
    public Movie minByMpaaRating() throws EmptyDatabaseException {
        return null;
    }

    @Override
    public int countGreaterThanOscarsCount(long oscarsCount) {
        return 0;
    }

    @Override
    public Set<Movie> filterByMpaaRating(MpaaRating rating) {
        return null;
    }
}
