package io.github.caesiumfox.lab05;

import io.github.caesiumfox.lab05.element.*;
import io.github.caesiumfox.lab05.exceptions.*;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * База данных с которой ведётся работа
 */
public class Database {
    /**
     * Вспомогательный класс для работы с JSON файлами
     * при помощи библиотеки GSON.
     * GSON работает напосредственно с ним,
     * преобразование между ним и
     * основным классом ({@link Database}) происходит
     * отдельно.
     */
    public static class RawData {
        public Date creationDate;
        public List<Movie.RawData> data;
    }

    private String inputFile;
    private final Date creationDate;
    private LinkedHashMap<Integer, Movie> data;
    private Set<String> knownPassportIDs;
    private int maxID;

    /**
     * Конструктор по умолчанию.
     * Создаёт пустую базу данных без
     * данных о файле-источнике, с
     * датой создания равной текущей
     */
    public Database() {
        inputFile = "";
        creationDate = new Date();
        knownPassportIDs = new HashSet<>();
        data = new LinkedHashMap<>();
        maxID = 0;
    }

    /**
     * Конструктор, инициализирующий базу данных
     * в соответствии с первичными данными,
     * полученными в результате чтения json файла
     * @param rawData Объект класса {@link RawData},
     * содержащий данные из json файла
     * @param inputFile Полный путь до файла-источника
     * @throws ElementIdAlreadyExistsException Если в ходе обработки будут повторяющиеся ключи
     * @throws PassportIdAlreadyExistsException Если в ходе обработки будут повторяющиеся номера паспортов
     * @throws StringLengthLimitationException Если в ходе обработки будут строки недопустимой длины
     * @throws CoordinatesOutOfRangeException Если в ходе обработки будут недопустимые значения координат
     * @throws NumberOutOfRangeException Если в ходе обработки будут недопустимые числовые значения
     * @throws NullPointerException Если в ходе обработки попадутся нулевые ссылки
     */
    public Database(RawData rawData, String inputFile) throws
            ElementIdAlreadyExistsException,
            PassportIdAlreadyExistsException,
            StringLengthLimitationException,
            CoordinatesOutOfRangeException,
            NumberOutOfRangeException {
        maxID = 0;
        this.inputFile = inputFile;
        Objects.requireNonNull(rawData.creationDate);
        this.creationDate = rawData.creationDate;
        this.knownPassportIDs = new HashSet<>();
        this.data = new LinkedHashMap<>();
        Objects.requireNonNull(rawData.data);
        for (Movie.RawData movieRawData : rawData.data) {
            if (this.data.containsKey(movieRawData.id)) {
                throw new ElementIdAlreadyExistsException(movieRawData.id);
            }
            if (this.knownPassportIDs.contains(movieRawData.director.passportID)) {
                throw new PassportIdAlreadyExistsException(movieRawData.director.passportID);
            }
            Objects.requireNonNull(movieRawData);
            if (movieRawData.id > maxID)
                maxID = movieRawData.id;
            this.knownPassportIDs.add(movieRawData.director.passportID);
            this.data.put(movieRawData.id, new Movie(movieRawData));
        }
    }

    /**
     * Возвращает имя файла, из которого
     * была считана база данных, или
     * пустую строку, если база данных
     * была создана с нуля.
     * @return Имя файла или пустая строка
     */
    public String getInputFile() {
        return inputFile;
    }

    /**
     * Возвращает дату создания базы данных.
     * @return Дата создания
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Проверяет наличие паспорта в базе данных
     * @param passportID Номер проверяемого паспорта
     * @return true, если паспорт есть, false, если нет
     */
    public boolean hasPassportID(String passportID) {
        return this.knownPassportIDs.contains(passportID);
    }

    /**
     * Перерасчитывает максимальное значение
     * идентификатора фильма
     */
    private void updateMaxID() {
        maxID = 0;
        for (Integer i : data.keySet()) {
            if (i > maxID)
                maxID = i;
        }
    }

    /**
     * Выводит в поток вывода информацию о
     * базе данных
     * @param output Поток вывода
     */
    public void info(PrintStream output) {
        output.println("--- Database info ---");
        output.print("  Input File:      ");
        output.println(this.inputFile.length() == 0 ? "<N/A>" : this.inputFile);
        output.print("  Creation Date:   ");
        output.println(new SimpleDateFormat(Main.dateFormat).format(this.creationDate));
        output.print("  Max ID:          ");
        output.println(this.maxID);
        output.print("  N/O Elements:    ");
        output.println(this.data.size());

        output.println("  Collection type: LinkedHashMap");
        output.println("  Fields:");
        output.println("    ID: [1 - 2147483647]");
        output.println("    Name: Not empty string");
        output.println("    Coordinates:");
        output.format ("      X: [%f, %f]\n", Coordinates.minX, Coordinates.maxX);
        output.format ("      Y: [%f, %f]\n", Coordinates.minY, Coordinates.maxY);
        output.format ("    Creation Date: %s\n", Main.dateFormat);
        output.println("    Oscars Count: [1 - 9223372036854775807]");
        output.format ("    Genre: %s\n", MovieGenre.listConstants());
        output.format ("    MPAA Rating: %s\n", MpaaRating.listConstants());
        output.println("    Director (may be null):");
        output.println("      Name: Not empty string");
        output.format ("      Passport ID (may be null): string with %d to %d characters\n",
                Person.passportIDMinLen, Person.passportIDMaxLen);
        output.format ("      Hair Color: %s\n", Color.listConstants());
    }

    /**
     * Выводит в поток вывода информацию о каждой
     * записи в базе данных.
     * @param output Поток вывода
     */
    public void show(PrintStream output) {
        if (data.isEmpty()) {
            output.println("  There are No Elements");
            return;
        }
        for (Movie movie : data.values()) {
            output.println(movie.toString());
        }
    }

    /**
     * Делает запись о фильме в базу данных,
     * меняя идентификатор на тот,
     * что на единицу больше наибольшего
     * уже существующего идентификатора
     * @param movie Запись о фильме
     * @throws RunOutOfIdsException Если
     * в базе данных есть элемент с максимально
     * возможным значением идентификатора
     * @throws PassportIdAlreadyExistsException Если
     * в базе данных уже есть запись о фильме,
     * номер паспорта режиссёра которого совпадает
     * с номером паспорта режиссёра новой записи
     */
    public void insert(Movie movie) throws RunOutOfIdsException,
            PassportIdAlreadyExistsException, NumberOutOfRangeException {
        if (maxID == Integer.MAX_VALUE) {
            throw new RunOutOfIdsException();
        }
        if (movie.hasPassportID()) {
            if (knownPassportIDs.contains(movie.getDirector().getPassportID())) {
                throw new PassportIdAlreadyExistsException(movie.getDirector().getPassportID());
            }
        }
        movie.setID(++maxID);
        data.put(movie.getID(), movie);
        if (movie.hasPassportID())
            knownPassportIDs.add(movie.getDirector().getPassportID());
    }

    /**
     * Делает запись о фильме в базу данных,
     * меняя идентификатор на новый
     * @param id Новый идентификатор
     * @param movie Запись о фильме
     * @throws ElementIdAlreadyExistsException Если
     * новый идентификатор уже занят
     * @throws PassportIdAlreadyExistsException Если
     * в базе данных уже есть запись о фильме,
     * номер паспорта режиссёра которого совпадает
     * с номером паспорта режиссёра новой записи
     * @throws NumberOutOfRangeException Если ключ не положительный
     */
    public void insert(Integer id, Movie movie)
            throws ElementIdAlreadyExistsException, PassportIdAlreadyExistsException,
            NumberOutOfRangeException {
        if(id <= 0)
            throw new NumberOutOfRangeException(id, 1, Integer.MAX_VALUE);
        if (data.containsKey(id)) {
            throw new ElementIdAlreadyExistsException(id);
        }
        if (movie.hasPassportID()) {
            if (knownPassportIDs.contains(movie.getDirector().getPassportID())) {
                throw new PassportIdAlreadyExistsException(movie.getDirector().getPassportID());
            }
        }
        if (id > maxID) {
            maxID = id;
        }
        movie.setID(id);
        data.put(id, movie);
        if (movie.hasPassportID())
            knownPassportIDs.add(movie.getDirector().getPassportID());
    }

    /**
     * Обновляет запись о фильме в базу данных,
     * по заданному идентификатору.
     * Дата создания записи обновляется.
     * @param id Идентификатор
     * @param movie Запись о фильме
     * @throws NoKeyInDatabaseException Если
     * в базе данных нет такого ключа
     * @throws PassportIdAlreadyExistsException Если
     * в базе данных уже есть запись о фильме,
     * номер паспорта режиссёра которого совпадает
     * с номером паспорта режиссёра новой записи
     */
    public void update(Integer id, Movie movie)
            throws NoKeyInDatabaseException, PassportIdAlreadyExistsException,
            NumberOutOfRangeException {
        if(id <= 0)
            throw new NumberOutOfRangeException(id, 1, Integer.MAX_VALUE);
        if (!data.containsKey(id)) {
            throw new NoKeyInDatabaseException(id);
        }

        if(data.get(id).hasPassportID()) {
            knownPassportIDs.remove(data.get(id).getDirector().getPassportID());
        }
        if(movie.hasPassportID()) {
            if(hasPassportID(movie.getDirector().getPassportID()))
                throw new PassportIdAlreadyExistsException(movie.getDirector().getPassportID());
            knownPassportIDs.add(movie.getDirector().getPassportID());
        }

        data.put(id, movie);
    }

    /**
     * Удаляет запись о фильме по его идентификатору (ключу).
     * @param id Идентификатор записи, которую нужно удалить
     * @throws NoKeyInDatabaseException Если нет
     * зфписи с указанным идентификатором
     */
    public void remove_key(Integer id) throws NoKeyInDatabaseException,
            NumberOutOfRangeException {
        if(id <= 0)
            throw new NumberOutOfRangeException(id, 1, Integer.MAX_VALUE);
        if (!data.containsKey(id)) {
            throw new NoKeyInDatabaseException(id);
        }
        if(data.get(id).hasPassportID())
        knownPassportIDs.remove(data.get(id).getDirector().getPassportID());
        data.remove(id);
        if (id == maxID) {
            updateMaxID();
        }
    }

    /**
     * Очищает базу данных, но сохраняет дату создания
     * и файл-источник.
     */
    public void clear() {
        knownPassportIDs.clear();
        data.clear();
        maxID = 0;
    }

    /**
     * Удаляет все записи, которые в соответствии
     * с компаратором {@link MovieComparator}
     * меньшие, чем заданная запись.
     * @param movie Запись, с которой производится сравнение
     */
    public void remove_lower(Movie movie) {
        MovieComparator comparator = new MovieComparator();
        for (Integer key : new HashSet<Integer>(data.keySet())) {
            if (comparator.compare(data.get(key), movie) < 0) {
                if(data.get(key).hasPassportID())
                knownPassportIDs.remove(data.get(key).getDirector().getPassportID());
                data.remove(key);
            }
        }
        updateMaxID();
    }

    /**
     * Удаляет все записи, идентификатор (ключ)
     * которых больше чем заданный.
     * @param id Значение ключа, с которым производится сравнение
     */
    public void remove_greater_key(Integer id) throws NumberOutOfRangeException {
        if(id <= 0)
            throw new NumberOutOfRangeException(id, 1, Integer.MAX_VALUE);
        for (Integer key : new HashSet<Integer>(data.keySet())) {
            if (key > id) {
                if(data.get(key).hasPassportID())
                knownPassportIDs.remove(data.get(key).getDirector().getPassportID());
                data.remove(key);
            }
        }
        updateMaxID();
    }

    /**
     * Удаляет все записи, идентификатор (ключ)
     * которых меньше чем заданный.
     * @param id Значение ключа, с которым производится сравнение
     */
    public void remove_lower_key(Integer id) throws NumberOutOfRangeException {
        if(id <= 0)
            throw new NumberOutOfRangeException(id, 1, Integer.MAX_VALUE);
        for (Integer key : new HashSet<Integer>(data.keySet())) {
            if (key < id) {
                if(data.get(key).hasPassportID())
                if(data.get(key).hasPassportID())
                knownPassportIDs.remove(data.get(key).getDirector().getPassportID());
                data.remove(key);
            }
        }
        updateMaxID();
    }

    /**
     * Возвращает любую запись с наименьшей
     * возрастной категорией
     * @return Запись с наименьшей возрастной категорей
     * @throws EmptyDatabaseException Если база
     * данных пуста
     */
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

    /**
     * Возвращает количество записей, в которых
     * количество оскаров больше, чем заданное
     * значение (0 в случае, если база данных пуста).
     * @param oscarsCount Количество оскаров, с которым
     * производится сравнение
     * @return Количество записей с числом оскаров
     * большим чем задано
     */
    public int count_greater_than_oscars_count(long oscarsCount) {
        int counter = 0;
        for (Movie movie : data.values()) {
            if (movie.getOscarsCount() > oscarsCount) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Возвращает множество всех записей с заданной
     * возрастной категорией. Если таких записей нет
     * или база данных пуста, возвращается пустое множество.
     * @param rating Искомая возрастная категория
     * @return Множество всех записей с заданной возрастной категорией
     */
    public Set<Movie> filter_by_mpaa_rating(MpaaRating rating) {
        HashSet<Movie> result = new HashSet<>();
        for (Movie movie : data.values()) {
            if (movie.getMpaaRating().compareTo(rating) == 0) {
                result.add(movie);
            }
        }
        return result;
    }

    /**
     * Преобразует объект класса в
     * соответсвующий ему объект
     * класса {@link RawData}
     * @return Объект класса {@link RawData}
     */
    public RawData toRawData() {
        RawData rawData = new RawData();
        rawData.creationDate = this.creationDate;
        rawData.data = new ArrayList<Movie.RawData>(this.data.size());
        for (Movie movie : this.data.values()) {
            rawData.data.add(movie.toRawData());
        }
        return rawData;
    }
}
