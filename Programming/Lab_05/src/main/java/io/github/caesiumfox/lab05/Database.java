package io.github.caesiumfox.lab05;

import io.github.caesiumfox.lab05.element.Movie;
import io.github.caesiumfox.lab05.element.MpaaRating;
import io.github.caesiumfox.lab05.exceptions.ElementIdAlreadyExistsException;
import io.github.caesiumfox.lab05.exceptions.PassportIdAlreadyExistsException;
import io.github.caesiumfox.lab05.exceptions.StringLengthLimitationException;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.HashSet;

public class Database {
    public static class Skeleton {
        public String name;
        public Date creationDate;
        public List<Movie.Skeleton> data;
    }

    private String inputFile;
    private String name;
    private final Date creationDate;
    private LinkedHashMap<Integer, Movie> data;
    private HashSet<String> knownPassportIDs;
    private int maxId;

    public Database() {
        this.name = "";
        creationDate = new Date();
        knownPassportIDs = new HashSet<>();
        data = new LinkedHashMap<>();
    }
    public Database(Skeleton skeleton) {
        this.name = skeleton.name;
        this.creationDate = skeleton.creationDate;
        this.knownPassportIDs = new HashSet<>();
        for(Movie.Skeleton movieSkeleton : skeleton.data) {
            if(this.data.containsKey(movieSkeleton.id)) {
                throw new ElementIdAlreadyExistsException(movieSkeleton.id);
            }
            if(this.knownPassportIDs.contains(movieSkeleton.director.passportID)) {
                throw new PassportIdAlreadyExistsException(movieSkeleton.director.passportID);
            }
            if(movieSkeleton.id > maxId)
                maxId = movieSkeleton.id;
            this.knownPassportIDs.add(movieSkeleton.director.passportID);
            this.data.put(movieSkeleton.id, new Movie(movieSkeleton));
        }
    }

    public String getInputFile() {
        return inputFile;
    }
    public String getName() {
        return name;
    }
    public Date getCreationDate() {
        return creationDate;
    }

    public void setInputFile(String inputFile) {
        if(inputFile == null) {
            throw new NullPointerException();
        }
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
        output.println(this.maxId);
    }
    public void show(PrintStream output) {

    }
    public void insert(Movie movie) {}
    public void update(Integer id, Movie movie) {}
    public void remove_key(Integer id) {}
    public void clear() {}
    public void save(PrintWriter output) {}
    public void remove_lower(Movie movie) {}
    public void remove_greater_key(Integer id) {}
    public void remove_lower_key(Integer id) {}
    public void min_by_mpaa_rating(PrintStream output) {}
    public void count_greater_than_oscars_count(long oscarsCount, PrintStream output) {}
    public void filter_by_mpaa_rating(MpaaRating rating, PrintStream output) {}
}
