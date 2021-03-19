package io.github.caesiumfox.lab05.element;

import io.github.caesiumfox.lab05.Database;
import io.github.caesiumfox.lab05.Main;
import io.github.caesiumfox.lab05.exceptions.*;

import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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


    public Movie(Skeleton skeleton) throws StringLengthLimitationException,
            CoordinatesOutOfRangeException, NumberOutOfRangeException {
        this.id = skeleton.id;
        setName(skeleton.name);
        setCoordinates(new Coordinates(skeleton.coordinates));
        this.creationDate = skeleton.creationDate;
        setOscarsCount(skeleton.oscarsCount);
        setGenre(skeleton.genre);
        setMpaaRating(skeleton.mpaaRating);
        setDirector(new Person(skeleton.director));
    }

    public Movie(Integer newID, PrintStream output, Scanner input, Database database) {
        this.id = newID;

        // name
        output.format("Enter the name (not empty):\n    ");
        while (true) {
            try {
                setName(input.nextLine().trim());
                break;
            } catch (StringLengthLimitationException e) {
                output.println("The name shouldn't be empty.");
                output.format("Enter the name again (not empty):\n    ");
            }
        }

        // coordinates
        Coordinates coordinates = new Coordinates();
        output.format("Enter X coordinate (from %.1f to %.1f):\n    ",
                Coordinates.minX, Coordinates.maxX);
        while (true) {
            try {
                String temp = input.nextLine().trim();
                coordinates.setX(Float.parseFloat(temp));
                break;
            } catch (CoordinatesOutOfRangeException | NumberFormatException e) {
                output.println(e.getMessage());
                output.format("Enter X coordinate again (from %f to %f):\n    ",
                        Coordinates.minX, Coordinates.maxX);
            }
        }
        output.format("Enter Y coordinate (from %f to %f):\n    ",
                Coordinates.minY, Coordinates.maxY);
        while (true) {
            try {
                String temp = input.nextLine().trim();
                coordinates.setY(Float.parseFloat(temp));
                break;
            } catch (CoordinatesOutOfRangeException | NumberFormatException e) {
                output.println(e.getMessage());
                output.format("Enter Y coordinate again (from %f to %f):\n    ",
                        Coordinates.minY, Coordinates.maxY);
            }
        }
        setCoordinates(coordinates);

        // creationDate
        this.creationDate = new Date();

        // oscarsCount
        output.format("Enter the number of Oscars (positive integer):\n    ");
        while (true) {
            try {
                String temp = input.nextLine().trim();
                setOscarsCount(Long.parseLong(temp));
                break;
            } catch (NumberOutOfRangeException | NumberFormatException e) {
                output.println(e.getMessage());
                output.format("Enter the number of Oscars again (positive integer):\n    ");
            }
        }

        // genre
        output.format("Enter the genre %s:\n    ", MovieGenre.listConstants());
        while (true) {
            try {
                setGenre(MovieGenre.fromString(input.nextLine().trim()));
                break;
            } catch (WrongEnumInputException e) {
                output.println(e.getMessage());
                output.format("Enter the genre again %s:\n    ", MovieGenre.listConstants());
            }
        }

        // mpaaRating
        output.format("Enter the MPAA rating %s:\n    ", MpaaRating.listConstants());
        while (true) {
            try {
                setMpaaRating(MpaaRating.fromString(input.nextLine().trim()));
                break;
            } catch (WrongEnumInputException e) {
                output.println(e.getMessage());
                output.format("Enter the MPAA rating again %s:\n    ", MpaaRating.listConstants());
            }
        }

        // director
        Person director = new Person();
        output.format("Enter the director's name :\n    ");
        while (true) {
            try {
                director.setName(input.nextLine().trim());
                break;
            } catch (StringLengthLimitationException e) {
                output.println(e.getMessage());
                output.format("Enter the director's name again:\n    ");
            }
        }
        output.format("Enter the director's passport ID (%d to %d symbols):\n    ",
                Person.passportIDMinLen, Person.passportIDMaxLen);
        while (true) {
            try {
                String passportID = input.nextLine().trim();
                if (database.hasPassportID(passportID))
                    throw new PassportIdAlreadyExistsException(passportID);
                director.setPassportID(passportID);
                break;
            } catch (StringLengthLimitationException | PassportIdAlreadyExistsException e) {
                output.println(e.getMessage());
                output.format("Enter the director's passport ID again (%d to %d symbols):\n    ",
                        Person.passportIDMinLen, Person.passportIDMaxLen);
            }
        }
        output.format("Enter the director's hair color %s:\n    ", Color.listConstants());
        while (true) {
            try {
                director.setHairColor(Color.fromString(input.nextLine().trim()));
                break;
            } catch (WrongEnumInputException e) {
                output.println(e.getMessage());
                output.format("Enter the director's hair color again %s:\n    ", Color.listConstants());
            }
        }
        setDirector(director);
    }

    public Integer getID() {
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

    public void setID(Integer id) {
        Objects.requireNonNull(id);
        this.id = id;
    }

    public void setName(String name) throws StringLengthLimitationException {
        Objects.requireNonNull(name);
        if (name.length() == 0) {
            throw new StringLengthLimitationException(name, 1, -1);
        }
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        Objects.requireNonNull(coordinates);
        this.coordinates = coordinates;
    }

    public void setOscarsCount(long oscarsCount) throws NumberOutOfRangeException {
        if (oscarsCount < 1) {
            throw new NumberOutOfRangeException(oscarsCount, 1, -1);
        }
        this.oscarsCount = oscarsCount;
    }

    public void setGenre(MovieGenre genre) {
        Objects.requireNonNull(genre);
        this.genre = genre;
    }

    public void setMpaaRating(MpaaRating mpaaRating) {
        Objects.requireNonNull(mpaaRating);
        this.mpaaRating = mpaaRating;
    }

    public void setDirector(Person director) {
        Objects.requireNonNull(director);
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
        result.append("    MPAA Rating: ").append(mpaaRating).append('\n');
        result.append("    Director:\n");
        result.append("        Name: ").append(director.getName()).append('\n');
        result.append("        Passport ID: ").append(director.getPassportID()).append('\n');
        result.append("        Hair Color: ").append(director.getHairColor()).append('\n');
        return result.toString();
    }

    public Skeleton toSkeleton() {
        Skeleton skeleton = new Skeleton();
        skeleton.id = this.id;
        skeleton.name = this.name;
        skeleton.coordinates = this.coordinates.toSkeleton();
        skeleton.creationDate = this.creationDate;
        skeleton.oscarsCount = this.oscarsCount;
        skeleton.genre = this.genre;
        skeleton.mpaaRating = this.mpaaRating;
        skeleton.director = this.director.toSkeleton();
        return skeleton;
    }
}
