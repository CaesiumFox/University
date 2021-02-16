package io.github.caesiumfox.lab05;

import io.github.caesiumfox.lab05.element.Movie;
import io.github.caesiumfox.lab05.element.MpaaRating;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.LinkedHashMap;

public class Database {
    private Date creationDate;
    private LinkedHashMap<Integer, Movie> data;

    public Database() {
        creationDate = new Date();
        data = new LinkedHashMap<Integer, Movie>();
    }

    public void info(PrintStream output) {}
    public void show(PrintStream output) {}
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
