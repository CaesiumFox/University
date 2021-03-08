package io.github.caesiumfox.lab05;

import io.github.caesiumfox.lab05.element.Movie;

import java.util.Comparator;

public class MovieComparator implements Comparator<Movie> {
    @Override
    public int compare(Movie m1, Movie m2) {
        double area1 = m1.getCoordinates().getX() * m1.getCoordinates().getY();
        double area2 = m2.getCoordinates().getX() * m2.getCoordinates().getY();

        if(area1 > area2)
            return 1;
        if(area1 < area2)
            return -1;
        return 0;
    }
}
