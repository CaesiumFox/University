package io.github.caesiumfox.lab05.element;

import io.github.caesiumfox.lab05.Main;
import io.github.caesiumfox.lab05.exceptions.NumberOutOfRangeException;
import io.github.caesiumfox.lab05.exceptions.StringLengthLimitationException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Movie {
    /**
     * Вспомогательный класс для работы с JSON файлами
     * при помощи библиотеки GSON.
     * GSON работает напосредственно с ним,
     * преобразование между "скелетом" и
     * базовым классом (Movie) происходит
     * отдельно.
     */
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
    private Date creationDate;
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


    public Movie(Integer id, String name, Coordinates coordinates, long oscarsCount,
                 MovieGenre genre, MpaaRating mpaaRating, Person director) {
        this.id = id;
        setName(name);
        setCoordinates(coordinates);
        this.creationDate = new Date();
        setOscarsCount(oscarsCount);
        setGenre(genre);
        setMpaaRating(mpaaRating);
        setDirector(director);
    }
    public Movie(Skeleton skeleton) {
        this.id = skeleton.id;
        setName(skeleton.name);
        setCoordinates(new Coordinates(skeleton.coordinates));
        this.creationDate = skeleton.creationDate;
        setOscarsCount(skeleton.oscarsCount);
        setGenre(skeleton.genre);
        setMpaaRating(skeleton.mpaaRating);
        setDirector(new Person(skeleton.director));
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Coordinates getCoordinates() {
        return coordinates;
    }
    public Date getCreationDate() {
        return creationDate;
    }
    public long getOscarsCount() {
        return oscarsCount;
    }
    public MovieGenre getGenre() {
        return genre;
    }
    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }
    public Person getDirector() {
        return director;
    }

    public void setName(String name) {
        if(name.length() == 0) {
            throw new StringLengthLimitationException(name, 1, -1);
        }
        if(name == null) {
            throw new NullPointerException();
        }
        this.name = name;
    }
    public void setCoordinates(Coordinates coordinates) {
        if(name == null) {
            throw new NullPointerException();
        }
        this.coordinates = coordinates;
    }
    public void setOscarsCount(long oscarsCount) {
        if(oscarsCount < 1) {
            throw new NumberOutOfRangeException(oscarsCount, 1, -1);
        }
        this.oscarsCount = oscarsCount;
    }
    public void setGenre(MovieGenre genre) {
        if(genre == null) {
            throw new NullPointerException();
        }
        this.genre = genre;
    }
    public void setMpaaRating(MpaaRating mpaaRating) {
        if(mpaaRating == null) {
            throw new NullPointerException();
        }
        this.mpaaRating = mpaaRating;
    }
    public void setDirector(Person director) {
        if(director == null) {
            throw new NullPointerException();
        }
        this.director = director;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        result.append("ID: ").append(id).append('\n');
        result.append("    Name: ").append(name).append('\n');
        result.append("    Coordinates:\n");
        result.append("        X: ").append(coordinates.getX()).append('\n');
        result.append("        Y: ").append(coordinates.getY()).append('\n');
        result.append("    Creation Date: ").append(
                new SimpleDateFormat(Main.dateFormat).format(this.creationDate)).append('\n');
        result.append("    N/O Oscars: ").append(oscarsCount).append('\n');
        result.append("    Genre: ").append(genre).append('\n');
        result.append("    MPAA Rating;").append(mpaaRating).append('\n');
        result.append("    Director:\n");
        result.append("        Name: ").append(director.getName()).append('\n');
        result.append("        Passport ID: ").append(director.getPassportID()).append('\n');
        result.append("        Hair Color: ").append(director.getHairColor()).append('\n');
        return result.toString();
    }
}
