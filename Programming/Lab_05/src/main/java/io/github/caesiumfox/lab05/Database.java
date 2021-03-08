package io.github.caesiumfox.lab05;

import io.github.caesiumfox.lab05.element.Movie;
import io.github.caesiumfox.lab05.element.MpaaRating;
import io.github.caesiumfox.lab05.exceptions.*;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class Database {
    public static class Skeleton {
        public Date creationDate;
        public List<Movie.Skeleton> data;
    }

    private String inputFile;
    private final Date creationDate;
    private LinkedHashMap<Integer, Movie> data;
    private Set<String> knownPassportIDs;
    private int maxID;

    public Database() {
        inputFile = "";
        creationDate = new Date();
        knownPassportIDs = new HashSet<>();
        data = new LinkedHashMap<>();
        maxID = 0;
    }

    public Database(Skeleton skeleton, String inputFile) {
        maxID = 0;
        this.inputFile = inputFile;
        this.creationDate = skeleton.creationDate;
        this.knownPassportIDs = new HashSet<>();
        this.data = new LinkedHashMap<>();
        for (Movie.Skeleton movieSkeleton : skeleton.data) {
            try {
                if (this.data.containsKey(movieSkeleton.id)) {
                    throw new ElementIdAlreadyExistsException(movieSkeleton.id);
                }
                if (this.knownPassportIDs.contains(movieSkeleton.director.passportID)) {
                    throw new PassportIdAlreadyExistsException(movieSkeleton.director.passportID);
                }
                if (movieSkeleton.id > maxID)
                    maxID = movieSkeleton.id;
                this.knownPassportIDs.add(movieSkeleton.director.passportID);
                this.data.put(movieSkeleton.id, new Movie(movieSkeleton));
            } catch (ElementIdAlreadyExistsException | PassportIdAlreadyExistsException |
                    StringLengthLimitationException | NumberOutOfRangeException |
                    CoordinatesOutOfRangeException e) {
                // Do something TODO
                System.err.println(e.getMessage());
            }
        }
    }

    public String getInputFile() {
        return inputFile;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setInputFile(String inputFile) throws StringLengthLimitationException {
        Objects.requireNonNull(inputFile);
        if (inputFile.length() == 0) {
            throw new StringLengthLimitationException(inputFile, 1, -1);
        }
        this.inputFile = inputFile;
    }

    public boolean hasPassportID(String passportID) {
        return this.knownPassportIDs.contains(passportID);
    }

    public void info(PrintStream output) {
        output.println("  --- Database info ---");
        output.print("    Input File:    ");
        output.println(this.inputFile.length() == 0 ? "<N/A>" : this.inputFile);
        output.print("    Creation Date: ");
        output.println(new SimpleDateFormat(Main.dateFormat).format(this.creationDate));
        output.print("    Max ID:        ");
        output.println(this.maxID);
        output.print("    N/O Elements:  ");
        output.println(this.data.size());
    }

    public void show(PrintStream output) {
        if (data.isEmpty()) {
            output.println("  There are No Elements");
            return;
        }
        for (Movie movie : data.values()) {
            output.println(movie.toString());
        }
    }

    public void insert(Movie movie) throws RunOutOfIdsException, PassportIdAlreadyExistsException {
        if (maxID == Integer.MAX_VALUE) {
            throw new RunOutOfIdsException();
        }
        if (knownPassportIDs.contains(movie.getDirector().getPassportID())) {
            throw new PassportIdAlreadyExistsException(movie.getDirector().getPassportID());
        }
        movie.setID(++maxID);
        data.put(movie.getID(), movie);
        knownPassportIDs.add(movie.getDirector().getPassportID());
    }

    public void insert(Integer id, Movie movie) throws ElementIdAlreadyExistsException {
        if (data.containsKey(id)) {
            throw new ElementIdAlreadyExistsException(id);
        }
        if (id > maxID) {
            maxID = id;
        }
        movie.setID(id);
        data.put(id, movie);
    }

    public void update(Integer id, Movie movie)
            throws NoKeyInDatabaseException, PassportIdAlreadyExistsException {
        if (!data.containsKey(id)) {
            throw new NoKeyInDatabaseException(id);
        }
        if (!data.get(id).getDirector().getPassportID().equals(movie.getDirector().getPassportID())) {
            knownPassportIDs.remove(data.get(id).getDirector().getPassportID());
            knownPassportIDs.add(movie.getDirector().getPassportID());
            throw new PassportIdAlreadyExistsException(movie.getDirector().getPassportID());
        }
        data.put(id, movie);
    }

    public void remove_key(Integer id) throws NoKeyInDatabaseException {
        if (!data.containsKey(id)) {
            throw new NoKeyInDatabaseException(id);
        }
        knownPassportIDs.remove(data.get(id).getDirector().getPassportID());
        data.remove(id);
        if (id == maxID) {
            maxID = 0;
            for (Integer i : data.keySet()) {
                if (i > maxID)
                    maxID = i;
            }
        }
    }

    public void clear() {
        knownPassportIDs.clear();
        data.clear();
        maxID = 0;
    }

    public void remove_lower(Movie movie) {
        MovieComparator comparator = new MovieComparator();
        for (Integer key : data.keySet()) {
            if (comparator.compare(data.get(key), movie) < 0) {
                knownPassportIDs.remove(data.get(key).getDirector().getPassportID());
                data.remove(key);
            }
        }
    }

    public void remove_greater_key(Integer id) {
        for (Integer key : data.keySet()) {
            if (key > id) {
                knownPassportIDs.remove(data.get(key).getDirector().getPassportID());
                data.remove(key);
            }
        }
    }

    public void remove_lower_key(Integer id) {
        for (Integer key : data.keySet()) {
            if (key < id) {
                knownPassportIDs.remove(data.get(key).getDirector().getPassportID());
                data.remove(key);
            }
        }
    }

    public Movie min_by_mpaa_rating() throws EmptyDatabaseException {
        MpaaRating minRating = MpaaRating.R;
        Movie minByRating = null;
        for (Movie movie : data.values()) {
            if (movie.getMpaaRating().compareTo(minRating) <= 0) {
                minRating = movie.getMpaaRating();
                minByRating = movie;
            }
        }
        if (minByRating == null) {
            throw new EmptyDatabaseException();
        }
        return minByRating;
    }

    public int count_greater_than_oscars_count(long oscarsCount) {
        int counter = 0;
        for (Movie movie : data.values()) {
            if (movie.getOscarsCount() > oscarsCount) {
                counter++;
            }
        }
        return counter;
    }

    public Set<Movie> filter_by_mpaa_rating(MpaaRating rating) {
        HashSet<Movie> result = new HashSet<>();
        for (Movie movie : data.values()) {
            if (movie.getMpaaRating().compareTo(rating) == 0) {
                result.add(movie);
            }
        }
        return result;
    }

    public Skeleton toSkeleton() {
        Skeleton skeleton = new Skeleton();
        skeleton.creationDate = this.creationDate;
        skeleton.data = new ArrayList<Movie.Skeleton>(this.data.size());
        for (Movie movie : this.data.values()) {
            skeleton.data.add(movie.toSkeleton());
        }
        return skeleton;
    }
}
