package io.github.caesiumfox.lab05;

import io.github.caesiumfox.lab05.element.Movie;
import io.github.caesiumfox.lab05.element.MpaaRating;
import io.github.caesiumfox.lab05.exceptions.*;

import java.io.PrintStream;
import java.io.PrintWriter;
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
        maxID= 0;
        this.inputFile = inputFile;
        this.creationDate = skeleton.creationDate;
        this.knownPassportIDs = new HashSet<>();
        this.data = new LinkedHashMap<>();
        for(Movie.Skeleton movieSkeleton : skeleton.data) {
            if(this.data.containsKey(movieSkeleton.id)) {
                throw new ElementIdAlreadyExistsException(movieSkeleton.id);
            }
            if(this.knownPassportIDs.contains(movieSkeleton.director.passportID)) {
                throw new PassportIdAlreadyExistsException(movieSkeleton.director.passportID);
            }
            if(movieSkeleton.id > maxID)
                maxID = movieSkeleton.id;
            this.knownPassportIDs.add(movieSkeleton.director.passportID);
            this.data.put(movieSkeleton.id, new Movie(movieSkeleton));
        }
    }

    public String getInputFile() {
        return inputFile;
    }
    public Date getCreationDate() {
        return creationDate;
    }

    public void setInputFile(String inputFile) {
        Objects.requireNonNull(inputFile);
        if(inputFile.length() == 0) {
            throw new StringLengthLimitationException(inputFile, 1, -1);
        }
        this.inputFile = inputFile;
    }

    public void info(PrintStream output) {
        output.println("  --- Database info ---");
        output.print(  "    Input File:    ");
        output.println(this.inputFile.length() == 0 ? "<N/A>" : this.inputFile);
        output.print(  "    Creation Date: ");
        output.println(new SimpleDateFormat(Main.dateFormat).format(this.creationDate));
        output.print(  "    Max ID:        ");
        output.println(this.maxID);
        output.print(  "    N/O Elements:  ");
        output.println(this.data.size());
    }
    public void show(PrintStream output) {
        if(data.isEmpty()) {
            output.println("  There are No Elements");
            return;
        }
        for(Map.Entry<Integer, Movie> entry : data.entrySet()) {
            output.println(entry.getValue().toString());
        }
    }
    public void insert(Movie movie) {
        if(maxID == Integer.MAX_VALUE) {
            throw new RunOutOfIdsException();
        }
        movie.setID(++maxID);
        data.put(movie.getID(), movie);
    }
    public void insert(Integer id, Movie movie) {
        if(data.containsKey(id)) {
            throw new ElementIdAlreadyExistsException(id);
        }
        if(id > maxID) {
            maxID = id;
        }
        movie.setID(id);
        data.put(id, movie);
    }
    public void update(Integer id, Movie movie) {
        if(!data.containsKey(id)) {
            throw new NoKeyInDatabaseException(id);
        }
        data.put(id, movie);
    }
    public void remove_key(Integer id) {
        if(!data.containsKey(id)) {
            throw new NoKeyInDatabaseException(id);
        }
        data.remove(id);
        if(id == maxID) {
            maxID = 0;
            for(Integer i : data.keySet()) {
                if(i > maxID)
                    maxID = i;
            }
        }
    }
    public void clear() {
        data.clear();
        maxID = 0;
    }
    public void save(PrintWriter output) {}
    public void remove_lower(Movie movie) {}
    public void remove_greater_key(Integer id) {}
    public void remove_lower_key(Integer id) {}
    //public Movie min_by_mpaa_rating() {}
    //public int count_greater_than_oscars_count(long oscarsCount) {}
    //public Set<Movie> filter_by_mpaa_rating(MpaaRating rating) {}
}
