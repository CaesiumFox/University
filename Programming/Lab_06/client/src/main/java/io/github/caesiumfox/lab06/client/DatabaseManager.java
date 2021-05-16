package io.github.caesiumfox.lab06.client;

import io.github.caesiumfox.lab06.common.Database;
import io.github.caesiumfox.lab06.common.MutableDatabaseInfo;
import io.github.caesiumfox.lab06.common.entry.Movie;
import io.github.caesiumfox.lab06.common.entry.MpaaRating;
import io.github.caesiumfox.lab06.common.exceptions.*;

import java.io.PrintStream;
import java.util.Date;
import java.util.Set;

public class DatabaseManager implements Database {
    MutableDatabaseInfo databaseInfo;

    public DatabaseManager() {

    }

    @Override
    public boolean hasPassportID(String passportId) {
        return false;
    }

    @Override
    public String getInputFile() {
        return null;
    }

    @Override
    public Date getCreationDate() {
        return null;
    }

    @Override
    public boolean hasID(Integer id) {
        return false;
    }

    @Override
    public boolean hasRanOutOfIDs() {
        return false;
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
