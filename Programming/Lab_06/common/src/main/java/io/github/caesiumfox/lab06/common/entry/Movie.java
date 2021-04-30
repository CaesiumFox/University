package io.github.caesiumfox.lab06.common.entry;

import com.google.gson.annotations.JsonAdapter;
import io.github.caesiumfox.lab05.Database;
import io.github.caesiumfox.lab05.Main;
import io.github.caesiumfox.lab05.exceptions.*;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Класс, описывающий фильм
 */
public class Movie {
    /**
     * Вспомогательный класс для работы с JSON файлами
     * при помощи библиотеки GSON.
     * GSON работает напосредственно с ним,
     * преобразование между "скелетом" и
     * базовым классом (Movie) происходит
     * отдельно.
     */
    public static class RawData {
        public int id;
        public String name;
        public Coordinates.RawData coordinates;
        public Date creationDate;
        public long oscarsCount;
        public MovieGenre genre;
        public MpaaRating mpaaRating;
        public Person.RawData director;

        /**
         * Определяет, есть ли у этой записи
         * значение в поле номера паспорта.
         * @return true, если паспорт есть
         */
        public boolean hasPassportID() {
            if (director == null)
                return false;
            if (director.passportID == null)
                return false;
            return true;
        }
    }


    private Integer id;
    private String name;
    private Coordinates coordinates;
    private Date creationDate;
    private long oscarsCount;
    private MovieGenre genre;
    private MpaaRating mpaaRating;
    private Person director;

    /**
     * Создаёт объект класса {@link Movie}
     * в соответствии с первичными данными,
     * полученными в результате чтения json файла.
     *
     * @param rawData Объект класса {@link RawData},
     *                содержащий данные из json файла
     * @throws StringLengthLimitationException Если в ходе обработки будут строки недопустимой длины
     * @throws CoordinatesOutOfRangeException  Если в ходе обработки будут недопустимые значения координат
     * @throws NumberOutOfRangeException       Если в ходе обработки будут недопустимые числовые значения
     */
    public Movie(RawData rawData) throws StringLengthLimitationException,
            CoordinatesOutOfRangeException, NumberOutOfRangeException {
        this.id = rawData.id;
        setName(rawData.name);
        setCoordinates(new Coordinates(rawData.coordinates));
        this.creationDate = rawData.creationDate;
        setOscarsCount(rawData.oscarsCount);
        setGenre(rawData.genre);
        setMpaaRating(rawData.mpaaRating);
        if(rawData.director == null)
            setDirector(null);
        else
            setDirector(new Person(rawData.director));
    }

    /**
     * Создаёт объект класса {@link Movie},
     * запуская интерактивный конструктор
     *
     * @param newID    Новый идентификатор,
     *                 совпадающий с ключом в базе данных
     * @param output   Интерактивный вывод
     * @param input    Интерактивный ввод
     * @param database База данных, в
     *                 которую будет сделана запись
     */
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
        boolean noDirector = false;
        Person director = new Person();
        output.format("Enter the director's name (or empty string if there is no director):\n    ");
        while (true) {
            try {
                director.setName(input.nextLine().trim());
                break;
            } catch (StringLengthLimitationException e) {
                noDirector = true;
                director = null;
                break;
            }
        }
        if (!noDirector) {
            output.format("Enter the director's passport ID (%d to %d symbols, or empty string if " +
                            "there is no passport ID):\n    ",
                    Person.passportIDMinLen, Person.passportIDMaxLen);
            while (true) {
                try {
                    String passportID = input.nextLine().trim();
                    if (passportID.isEmpty()) {
                        director.setPassportID(null);
                        break;
                    }
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
        }
        setDirector(director);
    }

    /**
     * Создаёт объект класса {@link Movie}
     * по аргументам, переданным в команду
     * insert, update или remove_lower.
     * @param commandArgs Аргументы команды
     * @param start Номер первого аргумента,
     * с которого идёт описание записи
     */
    public Movie(ArrayList<String> commandArgs, int start) throws
            StringLengthLimitationException, CoordinatesOutOfRangeException,
            NumberOutOfRangeException, WrongEnumInputException,
            IndexOutOfBoundsException {
        // id
        id = 0;
        // <name>
        setName(commandArgs.get(start++));
        // <coordinates.x>
        Coordinates coordinates = new Coordinates();
        coordinates.setX(Float.parseFloat(commandArgs.get(start++)));
        // <coordinates.y>
        coordinates.setY(Float.parseFloat(commandArgs.get(start++)));
        setCoordinates(coordinates);
        // creationDate
        creationDate = new Date();
        // <oscarsCount>
        setOscarsCount(Long.parseLong(commandArgs.get(start++)));
        // <genre>
        setGenre(MovieGenre.fromString(commandArgs.get(start++)));
        // <mpaaRating>
        setMpaaRating(MpaaRating.fromString(commandArgs.get(start++)));
        // [<director.name>
        if (commandArgs.size() <= start) {
            setDirector(null);
            return;
        }
        Person director = new Person();
        director.setName(commandArgs.get(start++));
        // [director.passportId]
        if (commandArgs.size() <= start + 1) { // one more field to go
            director.setPassportID(null);
        } else {
            director.setPassportID(commandArgs.get(start++));
        }
        // <director.hairColor>]
        director.setHairColor(Color.fromString(commandArgs.get(start++)));
        setDirector(director);
    }

    /**
     * Возвращает идентификатор, совпадающий
     * с ключом в базе данных
     *
     * @return Идентификатор
     */
    public Integer getID() {
        return id;
    }

    /**
     * Возвращает название фильма
     *
     * @return Название фильма
     */
    public String getName() {
        return name;
    }

    /**
     * Возращает координаты фильма
     *
     * @return Координаты фильма
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Возвращает дату создания записи о фильие
     *
     * @return Дата создания записи
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Возвращает количество оскаров у фильма
     *
     * @return Количество оскаров
     */
    public long getOscarsCount() {
        return oscarsCount;
    }

    /**
     * Возвращает жанр фильма
     *
     * @return Жанр фильма
     */
    public MovieGenre getGenre() {
        return genre;
    }

    /**
     * Возвращает возрастную категорию MPAA
     *
     * @return Возрастная категория
     */
    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    /**
     * Возвращает режиссёра фильма
     *
     * @return Режиссёр (может быть null)
     */
    public Person getDirector() {
        return director;
    }

    /**
     * Устанавливает идентификатор.
     * Идентификатор может быль любым.
     * Проверка на существование идентификатора в
     * базе данных не выполняется.
     *
     * @param id Идентификатор
     * @throws NumberOutOfRangeException Если ключ неположительный
     */
    public void setID(Integer id) throws NumberOutOfRangeException {
        Objects.requireNonNull(id);
        if (id <= 0)
            throw new NumberOutOfRangeException(id, 1, Integer.MAX_VALUE);
        this.id = id;
    }

    /**
     * Устанавливает название фильма
     *
     * @param name Название фильма
     * @throws StringLengthLimitationException Если
     *                                         название было пустым
     * @throws NullPointerException            Если
     *                                         было передано значение null
     */
    public void setName(String name) throws StringLengthLimitationException {
        Objects.requireNonNull(name);
        if (name.length() == 0) {
            throw new StringLengthLimitationException(name, 1, -1);
        }
        this.name = name;
    }

    /**
     * Устанавливает координаты фильма, уже
     * проверенные на нахождение в пределах
     * заданных промежутков
     *
     * @param coordinates Координаты фильма
     * @throws NullPointerException Если
     *                              было передано значение null
     */
    public void setCoordinates(Coordinates coordinates) {
        Objects.requireNonNull(coordinates);
        this.coordinates = coordinates;
    }

    /**
     * Устанавливает количество оскаров у фильма
     *
     * @param oscarsCount Количество оскаров
     * @throws NumberOutOfRangeException Если
     *                                   переданное количество оскаров не является
     *                                   натуральным числом
     */
    public void setOscarsCount(long oscarsCount) throws NumberOutOfRangeException {
        if (oscarsCount < 1) {
            throw new NumberOutOfRangeException(oscarsCount, 1, Long.MAX_VALUE);
        }
        this.oscarsCount = oscarsCount;
    }

    /**
     * Устанавливает жанр фильма
     *
     * @param genre Жанр фильма
     * @throws NullPointerException Если
     *                              было передано значение null
     */
    public void setGenre(MovieGenre genre) {
        Objects.requireNonNull(genre);
        this.genre = genre;
    }

    /**
     * Устанавливает возрастную категорию фильма
     *
     * @param mpaaRating Возрастная категория фильма
     * @throws NullPointerException Если
     *                              было передано значение null
     */
    public void setMpaaRating(MpaaRating mpaaRating) {
        Objects.requireNonNull(mpaaRating);
        this.mpaaRating = mpaaRating;
    }

    /**
     * Устанавливает режиссёра фильма, имя
     * и номер пасспорта которого уже
     * проверены на длину.
     *
     * @param director Режиссёр фильма (может быть null)
     */
    public void setDirector(Person director) {
        this.director = director;
    }

    /**
     * Определяет, есть ли у этой записи
     * значение в поле номера паспорта.
     *
     * @return true, если паспорт есть
     */
    public boolean hasPassportID() {
        if (director == null)
            return false;
        if (director.getPassportID() == null)
            return false;
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        result.append("ID: ").append(id).append('\n');
        result.append("  Name: ").append(name).append('\n');
        result.append("  Coordinates:\n");
        result.append("    X: ").append(coordinates.getX()).append('\n');
        result.append("    Y: ").append(coordinates.getY()).append('\n');
        result.append("  Creation Date: ").append(
                new SimpleDateFormat(Main.dateFormat).format(this.creationDate)).append('\n');
        result.append("  Oscars Count: ").append(oscarsCount).append('\n');
        result.append("  Genre: ").append(genre).append('\n');
        result.append("  MPAA Rating: ").append(mpaaRating).append('\n');
        if (director == null) {
            result.append("  Director: <N/A>\n");
        } else {
            result.append("  Director:\n");
            result.append("    Name: ").append(director.getName()).append('\n');
            result.append("    Passport ID: ");
            if (director.getPassportID() == null) {
                result.append("<N/A>");
            } else {
                result.append(director.getPassportID());
            }
            result.append('\n');
            result.append("    Hair Color: ").append(director.getHairColor()).append('\n');
        }
        return result.toString();
    }

    /**
     * Преобразует объект класса в
     * соответсвующий ему объект
     * класса {@link RawData}
     *
     * @return Объект класса {@link RawData}
     */
    public RawData toRawData() {
        RawData rawData = new RawData();
        rawData.id = this.id;
        rawData.name = this.name;
        rawData.coordinates = this.coordinates.toRawData();
        rawData.creationDate = this.creationDate;
        rawData.oscarsCount = this.oscarsCount;
        rawData.genre = this.genre;
        rawData.mpaaRating = this.mpaaRating;
        if (this.director == null)
            rawData.director = null;
        else
            rawData.director = this.director.toRawData();
        return rawData;
    }
}
