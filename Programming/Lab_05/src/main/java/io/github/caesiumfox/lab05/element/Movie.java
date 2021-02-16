package io.github.caesiumfox.lab05.element;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Movie {
    public static class Skeleton {
        public int id;
        public String name;
        public Coordinates.Skeleton coordinates;
        public Date creationDate;
        public long oscarsCount;
        public MovieGenre genre;
        public MpaaRating mpaaRating;
        public Person.Skeleton director;
    }

    /**
     * Не может быть null, Значение поля должно быть больше 0,
     * Значение этого поля должно быть уникальным,
     * Значение этого поля должно генерироваться автоматически
     */
    private Integer id;
    /**
     * Не может быть null,
     * Не может быть пустой
     */
    private String name;
    /**
     * Не может быть null
     */
    private Coordinates coordinates;
    /**
     * Не может быть null, Значение поля должно быть больше 0,
     * Значение этого поля должно генерироваться автоматически
     */
    private java.util.Date creationDate;
    /**
     * Значение поля больше 0
     */
    private long oscarsCount;
    /**
     * Не может быть null
     */
    private MovieGenre genre;
    /**
     * Не может быть null
     */
    private MpaaRating mpaaRating;
    /**
     * Не может быть null
     */
    private Person director;


    public Movie() {}
    public Movie(io.github.caesiumfox.lab05.parsingSkeleton.Movie skeleton) {}
}
