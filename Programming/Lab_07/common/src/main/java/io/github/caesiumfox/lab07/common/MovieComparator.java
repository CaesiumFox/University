package io.github.caesiumfox.lab07.common;

import io.github.caesiumfox.lab07.common.entry.Movie;

import java.util.Comparator;

/**
 * Производит сравнение двух записей о фильмах.
 * Используется командой remove_lower.
 */
public class MovieComparator implements Comparator<Movie> {
    /**
     * Сравнивает две записи о фильмах.
     * Результат совпадает с результатом сравнения
     * произвеления полей coordinates.x и coordinates.y.
     */
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
