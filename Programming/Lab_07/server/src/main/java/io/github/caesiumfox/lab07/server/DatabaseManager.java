package io.github.caesiumfox.lab07.server;

import io.github.caesiumfox.lab07.common.MovieComparator;
import io.github.caesiumfox.lab07.common.MutableDatabaseInfo;
import io.github.caesiumfox.lab07.common.entry.*;
import io.github.caesiumfox.lab07.common.exceptions.*;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * База данных с которой ведётся работа
 */
public class DatabaseManager {
    /**
     * Вспомогательный класс для работы с JSON файлами
     * при помощи библиотеки GSON.
     * GSON работает непосредственно с ним,
     * преобразование между ним и
     * основным классом ({@link DatabaseManager}) происходит
     * отдельно.
     */
    public static class RawData {
        public Date creationDate;
        public List<Movie.RawData> data;
    }

    private final java.util.Date creationDate;
    private final LinkedHashMap<Integer, Movie> data;
    private final Set<String> knownPassportIDs;
    private int maxID;
    private final ReentrantLock dataLock;
    private final ReentrantLock passportLock;
    private final ReentrantLock creationDateLock;
    private final ReentrantLock maxIdLock;

    /**
     * Конструктор по умолчанию.
     * Создаёт пустую базу данных без
     * данных о файле-источнике, с
     * датой создания равной текущей
     */
    public DatabaseManager(Scanner input) throws java.sql.SQLException,
            ElementIdAlreadyExistsException,
            PassportIdAlreadyExistsException,
            StringLengthLimitationException,
            CoordinatesOutOfRangeException,
            NumberOutOfRangeException {
        Server.logger.info("Started database manager initialization");

        ResultSet creationDateResultSet = Server.universalStatement.executeQuery("select * from meta;");
        if(creationDateResultSet.next()) {
            creationDate = creationDateResultSet.getDate(1);
        } else {
            throw new SQLException("No CreationDate for the database specified.");
        }
        updateMaxID();
        dataLock = new ReentrantLock();
        passportLock = new ReentrantLock();
        creationDateLock = new ReentrantLock();
        maxIdLock = new ReentrantLock();

        this.knownPassportIDs = new HashSet<>();
        this.data = new LinkedHashMap<>();

        ResultSet dbResultSet = Server.universalStatement.executeQuery("select * from movies;");
        while(dbResultSet.next()) {
            Movie.RawData movieRawData = new Movie.RawData();
            movieRawData.id = dbResultSet.getInt("id");
            movieRawData.name = dbResultSet.getString("name");
            movieRawData.coordinates = new Coordinates.RawData();
            movieRawData.coordinates.x = dbResultSet.getFloat("x");
            movieRawData.coordinates.y = dbResultSet.getFloat("y");
            movieRawData.creationDate = dbResultSet.getDate("creationdate");
            movieRawData.oscarsCount = dbResultSet.getLong("oscars");
            movieRawData.genre = MovieGenre.fromOrdinal(dbResultSet.getInt("genre"));
            movieRawData.mpaaRating = MpaaRating.fromOrdinal(dbResultSet.getInt("rating"));
            String directorName = dbResultSet.getString("dirname");
            if(directorName == null) {
                movieRawData.director = null;
            } else {
                movieRawData.director = new Person.RawData();
                movieRawData.director.name = directorName;
                movieRawData.director.passportID = dbResultSet.getString("dirpassport");
                movieRawData.director.hairColor = Color.fromOrdinal(dbResultSet.getInt("dirhaircol"));
            }
            data.put(movieRawData.id, new Movie(movieRawData));
        }

        Server.logger.info("Initialized database manager");
    }

    private void updateMaxID() throws SQLException {
        maxIdLock.lock();
        ResultSet maxIdResultSet = Server.universalStatement.executeQuery("select max(id) from movies;");
        if (maxIdResultSet.next()) {
            maxID = maxIdResultSet.getInt(1);
        } else {
            throw new SQLException("Couldn't select max(id) from movies");
        }
        maxIdLock.unlock();
    }

    /**
     * Возвращает дату создания базы данных.
     *
     * @return Дата создания
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Проверяет наличие паспорта в базе данных
     *
     * @param passportID Номер проверяемого паспорта
     * @return true, если паспорт есть, false, если нет
     */
    public boolean hasPassportID(String passportID) {
        passportLock.lock();
        boolean result = knownPassportIDs.contains(passportID);
        passportLock.unlock();
        return result;
    }

    public boolean hasID(Integer id) {
        dataLock.lock();
        boolean result = data.containsKey(id);
        dataLock.unlock();
        return result;
    }

    public boolean hasRanOutOfIDs() {
        maxIdLock.lock();
        boolean result = (maxID == Integer.MAX_VALUE);
        maxIdLock.unlock();
        return result;
    }

    public MutableDatabaseInfo getMutableInfo() {
        MutableDatabaseInfo info = new MutableDatabaseInfo();
        info.setCreationDate(this.creationDate);
        info.setMaxID(this.maxID);
        dataLock.lock();
        info.setNumberOfElements(this.data.size());
        dataLock.unlock();
        return info;
    }

    public List<Movie> getAllElements() {
        dataLock.lock();
        List<Movie> result = data.values().stream()
                .sorted(new MovieComparator())
                .collect(Collectors.toList());
        dataLock.unlock();
        return result;
    }

    /**
     * Делает запись о фильме в базу данных,
     * меняя идентификатор на тот,
     * что на единицу больше наибольшего
     * уже существующего идентификатора
     *
     * @param movie Запись о фильме
     * @throws RunOutOfIdsException             Если
     *                                          в базе данных есть элемент с максимально
     *                                          возможным значением идентификатора
     * @throws PassportIdAlreadyExistsException Если
     *                                          в базе данных уже есть запись о фильме,
     *                                          номер паспорта режиссёра которого совпадает
     *                                          с номером паспорта режиссёра новой записи
     */
    public void insert(Movie movie, String owner) throws RunOutOfIdsException,
            PassportIdAlreadyExistsException, NumberOutOfRangeException, SQLException {
        // TODO
        if (hasRanOutOfIDs()) {
            throw new RunOutOfIdsException();
        }
        if (movie.hasPassportID()) {
            if (hasPassportID(movie.getDirector().getPassportID())) {
                throw new PassportIdAlreadyExistsException(movie.getDirector().getPassportID());
            }
        }
        movie.updateCreationDate();


        PreparedStatement insertStatement = Server.connection.prepareStatement(
                "insert into movies values (nextval('IntID')," +
                        "?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        insertStatement.setString(1, movie.getName());
        insertStatement.setFloat(2, movie.getCoordinates().getX());
        insertStatement.setFloat(3, movie.getCoordinates().getY());
        insertStatement.setDate(4, new java.sql.Date(movie.getCreationDate().getTime()));
        insertStatement.setLong(5, movie.getOscarsCount());
        insertStatement.setInt(6, movie.getGenre().ordinal());
        insertStatement.setInt(7, movie.getMpaaRating().ordinal());
        if (movie.getDirector() == null) {
            insertStatement.setString(8, null);
            insertStatement.setString(9, null);
            insertStatement.setString(10, null);
        } else {
            insertStatement.setString(8, movie.getDirector().getName());
            insertStatement.setString(9, movie.getDirector().getPassportID());
            insertStatement.setInt(10, movie.getDirector().getHairColor().ordinal());
        }
        boolean ok = insertStatement.execute();
        // if inserting is correct
        maxID++;
        data.put(movie.getID(), movie);
        /*data = data.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));*/
    }

    /**
     * Делает запись о фильме в базу данных,
     * меняя идентификатор на новый
     *
     * @param id    Новый идентификатор
     * @param movie Запись о фильме
     * @throws ElementIdAlreadyExistsException  Если
     *                                          новый идентификатор уже занят
     * @throws PassportIdAlreadyExistsException Если
     *                                          в базе данных уже есть запись о фильме,
     *                                          номер паспорта режиссёра которого совпадает
     *                                          с номером паспорта режиссёра новой записи
     * @throws NumberOutOfRangeException        Если ключ не положительный
     */
    public void insert(Integer id, Movie movie, String owner)
            throws ElementIdAlreadyExistsException, PassportIdAlreadyExistsException,
            NumberOutOfRangeException, SQLException {
        // TODO
        if (id <= 0)
            throw new NumberOutOfRangeException(id, 1, Integer.MAX_VALUE);
        if (hasID(id)) {
            throw new ElementIdAlreadyExistsException(id);
        }
        if (movie.hasPassportID()) {
            if (hasPassportID(movie.getDirector().getPassportID())) {
                throw new PassportIdAlreadyExistsException(movie.getDirector().getPassportID());
            }
        }
        if (id > maxID) {
            maxID = id;
        }
        movie.updateCreationDate();
        movie.setID(id);
        data.put(id, movie);
    }

    /**
     * Обновляет запись о фильме в базу данных,
     * по заданному идентификатору.
     * Дата создания записи обновляется.
     *
     * @param id    Идентификатор
     * @param movie Запись о фильме
     * @throws NoKeyInDatabaseException         Если
     *                                          в базе данных нет такого ключа
     * @throws PassportIdAlreadyExistsException Если
     *                                          в базе данных уже есть запись о фильме,
     *                                          номер паспорта режиссёра которого совпадает
     *                                          с номером паспорта режиссёра новой записи
     */
    public void update(Integer id, Movie movie, String owner)
            throws NoKeyInDatabaseException, PassportIdAlreadyExistsException,
            NumberOutOfRangeException, SQLException {
        // TODO
        if (id <= 0)
            throw new NumberOutOfRangeException(id, 1, Integer.MAX_VALUE);
        if (!hasID(id)) {
            throw new NoKeyInDatabaseException(id);
        }

        if (movie.hasPassportID()) {
            if (hasPassportID(movie.getDirector().getPassportID()))
                throw new PassportIdAlreadyExistsException(movie.getDirector().getPassportID());
        }

        movie.updateCreationDate();
        data.put(id, movie);
    }

    /**
     * Удаляет запись о фильме по его идентификатору (ключу).
     *
     * @param id Идентификатор записи, которую нужно удалить
     * @throws NoKeyInDatabaseException Если нет
     *                                  записи с указанным идентификатором
     */
    public void removeKey(Integer id, String owner) throws NoKeyInDatabaseException,
            NumberOutOfRangeException, SQLException {
        // TODO
        if (id <= 0)
            throw new NumberOutOfRangeException(id, 1, Integer.MAX_VALUE);
        if (!hasID(id)) {
            throw new NoKeyInDatabaseException(id);
        }
        data.remove(id);
        if (id == maxID) {
            updateMaxID();
        }
    }

    /**
     * Очищает базу данных, но сохраняет дату создания
     * и файл-источник.
     */
    public void clear(String owner) throws SQLException {
        // TODO
        try {
            dataLock.lock();
            passportLock.lock();
            maxIdLock.lock();


            data.clear();
            knownPassportIDs.clear();
            maxID = 0;
        } finally {
            dataLock.unlock();
            passportLock.unlock();
            maxIdLock.unlock();
        }
    }

    /**
     * Удаляет все записи, которые в соответствии
     * с компаратором {@link MovieComparator}
     * меньшие, чем заданная запись.
     *
     * @param movie Запись, с которой производится сравнение
     */
    public void removeLower(Movie movie, String owner) throws SQLException {
        // TODO
        movie.updateCreationDate();
        MovieComparator comparator = new MovieComparator();
        for (Integer key : new HashSet<Integer>(data.keySet())) {
            if (comparator.compare(data.get(key), movie) < 0) {
                data.remove(key);
            }
        }
        updateMaxID();
    }

    /**
     * Удаляет все записи, идентификатор (ключ)
     * которых больше чем заданный.
     *
     * @param id Значение ключа, с которым производится сравнение
     */
    public void removeGreaterKey(Integer id, String owner)
            throws NumberOutOfRangeException, SQLException {
        // TODO
        if (id <= 0)
            throw new NumberOutOfRangeException(id, 1, Integer.MAX_VALUE);
        for (Integer key : new HashSet<Integer>(data.keySet())) {
            if (key > id) {
                data.remove(key);
            }
        }
        updateMaxID();
    }

    /**
     * Удаляет все записи, идентификатор (ключ)
     * которых меньше чем заданный.
     *
     * @param id Значение ключа, с которым производится сравнение
     */
    public void removeLowerKey(Integer id, String owner)
            throws NumberOutOfRangeException, SQLException {
        // TODO
        if (id <= 0)
            throw new NumberOutOfRangeException(id, 1, Integer.MAX_VALUE);
        for (Integer key : new HashSet<Integer>(data.keySet())) {
            if (key < id) {
                if (data.get(key).hasPassportID())
                data.remove(key);
            }
        }
        updateMaxID();
    }

    /**
     * Возвращает любую запись с наименьшей
     * возрастной категорией
     *
     * @return Запись с наименьшей возрастной категорией
     * @throws EmptyDatabaseException Если база
     *                                данных пуста
     */
    public synchronized Movie minByMpaaRating()
            throws EmptyDatabaseException {
        try {
            dataLock.lock();
            if (data.size() == 0)
                throw new EmptyDatabaseException();
            Movie result = data.values().stream()
                    .min((Movie a, Movie b) -> {
                        return a.getMpaaRating().ordinal() - b.getMpaaRating().ordinal();
                    }).get();
            return result;
        } finally {
            dataLock.unlock();
        }
    }

    /**
     * Возвращает количество записей, в которых
     * количество оскаров больше, чем заданное
     * значение (0 в случае, если база данных пуста).
     *
     * @param oscarsCount Количество оскаров, с которым
     *                    производится сравнение
     * @return Количество записей с числом оскаров
     * большим чем задано
     */
    public int countGreaterThanOscarsCount(long oscarsCount) {
        dataLock.lock();
        int result = (int) data.values().stream()
                .filter((Movie movie) -> {
                    return movie.getOscarsCount() > oscarsCount;
                })
                .count();
        dataLock.unlock();
        return result;
    }

    /**
     * Возвращает множество всех записей с заданной
     * возрастной категорией. Если таких записей нет
     * или база данных пуста, возвращается пустое множество.
     *
     * @param rating Искомая возрастная категория
     * @return Множество всех записей с заданной возрастной категорией
     */
    public List<Movie> filterByMpaaRating(MpaaRating rating) {
        dataLock.lock();
        List<Movie> result = data.values().stream()
                .filter((Movie movie) -> movie.getMpaaRating() == rating)
                .collect(Collectors.toList());
        dataLock.unlock();
        return result;
    }

    int generateNewId() throws SQLException {
        try {
            dataLock.lock();
            while (true) {
                ResultSet nextInt = Server.universalStatement
                        .executeQuery("select nextval('intid')");
                if(nextInt.next()) {
                    int id = nextInt.getInt(1);
                    if (!data.containsKey(id)) {
                        return id;
                    }
                } else {
                    throw new SQLException("ID sequence error");
                }
            }
        } finally {
            dataLock.unlock();
        }
    }

    /**
     * Преобразует объект класса в
     * соответствующий ему объект
     * класса {@link RawData}
     *
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
