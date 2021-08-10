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
    private final LinkedHashMap<Integer, String> owners;
    private final Set<String> knownPassportIDs;
    private int maxID;
    private final ReentrantLock dataLock;
    private final ReentrantLock passportLock;
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
        dataLock = new ReentrantLock();
        passportLock = new ReentrantLock();
        maxIdLock = new ReentrantLock();

        this.knownPassportIDs = new HashSet<>();
        this.data = new LinkedHashMap<>();
        this.owners = new LinkedHashMap<>();

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
        ResultSet ownersResultSet = Server.universalStatement.executeQuery("select * from owners;");
        while(dbResultSet.next()) {
            int id = ownersResultSet.getInt("movieid");
            String name = ownersResultSet.getString("username");
            owners.put(id, name);
        }
        updateMaxID();

        Server.logger.info("Initialized database manager");
    }

    private void updateMaxID() {
        maxIdLock.lock();
        maxID = data.keySet().stream().max(Comparator.comparingInt((a) -> a)).orElse(0);
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
        try {
            dataLock.lock();
            return data.values().stream()
                    .sorted(new MovieComparator())
                    .collect(Collectors.toList());
        } finally {
            dataLock.unlock();
        }
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
        if (hasRanOutOfIDs()) {
            throw new RunOutOfIdsException();
        }
        while (true) {
            try {
                insert(generateNewId(), movie, owner);
                break;
            } catch (ElementIdAlreadyExistsException ignored) {}
        }
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
        try {
            dataLock.lock();
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

            movie.updateCreationDate();
            movie.setID(id);

            PreparedStatement insertStatement = Server.connection.prepareStatement(
                    "insert into movies values " +
                            "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            insertStatement.setInt(1, id);
            insertStatement.setString(2, movie.getName());
            insertStatement.setFloat(3, movie.getCoordinates().getX());
            insertStatement.setFloat(4, movie.getCoordinates().getY());
            insertStatement.setDate(5, new java.sql.Date(movie.getCreationDate().getTime()));
            insertStatement.setLong(6, movie.getOscarsCount());
            insertStatement.setInt(7, movie.getGenre().ordinal());
            insertStatement.setInt(8, movie.getMpaaRating().ordinal());
            if (movie.getDirector() == null) {
                insertStatement.setString(9, null);
                insertStatement.setString(10, null);
                insertStatement.setString(11, null);
            } else {
                insertStatement.setString(9, movie.getDirector().getName());
                insertStatement.setString(10, movie.getDirector().getPassportID());
                insertStatement.setInt(11, movie.getDirector().getHairColor().ordinal());
            }
            insertStatement.execute();

            data.put(id, movie);
            if (movie.hasPassportID())
                knownPassportIDs.add(movie.getDirector().getPassportID());
            owners.put(id, owner);
            maxIdLock.lock();
            if (id > maxID)
                maxID = id;
            maxIdLock.unlock();
        } finally {
            dataLock.unlock();
        }
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
            NumberOutOfRangeException, NotAnOwnerException, SQLException {
        try {
            dataLock.lock();
            if (id <= 0)
                throw new NumberOutOfRangeException(id, 1, Integer.MAX_VALUE);
            if (!hasID(id))
                throw new NoKeyInDatabaseException(id);
            if (!owners.get(id).equals(owner))
                throw new NotAnOwnerException(owner, id);
            if (movie.hasPassportID()) {
                if (hasPassportID(movie.getDirector().getPassportID()))
                    throw new PassportIdAlreadyExistsException(movie.getDirector().getPassportID());
            }
            movie.updateCreationDate();

            PreparedStatement updateStatement = Server.connection.prepareStatement(
                    "update movies set " +
                            "(name, x, y, creationdate, oscars, " +
                            "genre, rating, dirname, dirpassport, dirhaircol)" +
                            " = (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                            "where id=?");
            updateStatement.setString(1, movie.getName());
            updateStatement.setFloat(2, movie.getCoordinates().getX());
            updateStatement.setFloat(3, movie.getCoordinates().getY());
            updateStatement.setDate(4, new java.sql.Date(movie.getCreationDate().getTime()));
            updateStatement.setLong(5, movie.getOscarsCount());
            updateStatement.setInt(6, movie.getGenre().ordinal());
            updateStatement.setInt(7, movie.getMpaaRating().ordinal());
            if (movie.getDirector() == null) {
                updateStatement.setString(8, null);
                updateStatement.setString(9, null);
                updateStatement.setString(10, null);
            } else {
                updateStatement.setString(8, movie.getDirector().getName());
                updateStatement.setString(9, movie.getDirector().getPassportID());
                updateStatement.setInt(10, movie.getDirector().getHairColor().ordinal());
            }
            updateStatement.setInt(11, id);
            updateStatement.execute();
            Movie prev = data.put(id, movie);
            assert prev != null;
            try {
                passportLock.lock();
                if (prev.hasPassportID())
                    knownPassportIDs.remove(prev.getDirector().getPassportID());
                if (movie.hasPassportID())
                    knownPassportIDs.add(movie.getDirector().getPassportID());
            } finally {
                passportLock.unlock();
            }
        } finally {
            dataLock.unlock();
        }
    }

    /**
     * Удаляет запись о фильме по его идентификатору (ключу).
     *
     * @param id Идентификатор записи, которую нужно удалить
     * @throws NoKeyInDatabaseException Если нет
     *                                  записи с указанным идентификатором
     */
    public void removeKey(Integer id, String owner) throws NoKeyInDatabaseException,
            NumberOutOfRangeException, NotAnOwnerException, SQLException {
        try {
            dataLock.lock();
            passportLock.lock();
            maxIdLock.lock();

            if (owners.get(id).equals(owner)) {
                removeElement(id);
            } else {
                throw new NotAnOwnerException(owner, id);
            }

            if (id == maxID)
                updateMaxID();
        } finally {
            dataLock.unlock();
            passportLock.unlock();
            maxIdLock.unlock();
        }
    }

    /**
     * Очищает базу данных, но сохраняет дату создания
     * и файл-источник.
     */
    public void clear(String owner) throws SQLException {
        try {
            dataLock.lock();
            passportLock.lock();
            maxIdLock.lock();

            for (Integer key : new HashSet<Integer>(data.keySet())) {
                if (owners.get(key).equals(owner)) {
                    removeElement(key);
                }
            }

            updateMaxID();
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
        try {
            dataLock.lock();
            movie.updateCreationDate();
            MovieComparator comparator = new MovieComparator();
            for (Integer key : new HashSet<Integer>(data.keySet())) {
                if (comparator.compare(data.get(key), movie) < 0
                        && owners.get(key).equals(owner)) {
                    removeElement(key);
                }
            }
        } finally {
            maxIdLock.lock();
            updateMaxID();
            maxIdLock.unlock();
            dataLock.unlock();
        }
    }

    /**
     * Удаляет все записи, идентификатор (ключ)
     * которых больше чем заданный.
     *
     * @param id Значение ключа, с которым производится сравнение
     */
    public void removeGreaterKey(Integer id, String owner)
            throws NumberOutOfRangeException, SQLException {
        try {
            if (id <= 0)
                throw new NumberOutOfRangeException(id, 1, Integer.MAX_VALUE);
            for (Integer key : new HashSet<Integer>(data.keySet())) {
                if (key > id) {
                    removeElement(key);
                }
            }
        } finally {
            maxIdLock.lock();
            updateMaxID();
            maxIdLock.unlock();
            dataLock.unlock();
        }
    }

    /**
     * Удаляет все записи, идентификатор (ключ)
     * которых меньше чем заданный.
     *
     * @param id Значение ключа, с которым производится сравнение
     */
    public void removeLowerKey(Integer id, String owner)
            throws NumberOutOfRangeException, SQLException {
        try {
            if (id <= 0)
                throw new NumberOutOfRangeException(id, 1, Integer.MAX_VALUE);
            for (Integer key : new HashSet<Integer>(data.keySet())) {
                if (key < id) {
                    removeElement(key);
                }
            }
        } finally {
            maxIdLock.lock();
            updateMaxID();
            maxIdLock.unlock();
            dataLock.unlock();
        }
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
            return data.values().stream()
                    .min((Movie a, Movie b) -> {
                        return a.getMpaaRating().ordinal() - b.getMpaaRating().ordinal();
                    }).get();
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
        try {
            dataLock.lock();
            return (int) data.values().stream()
                    .filter((Movie movie) -> {
                        return movie.getOscarsCount() > oscarsCount;
                    })
                    .count();
        } finally {
            dataLock.unlock();
        }
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
        try {
            dataLock.lock();
            return data.values().stream()
                    .filter((Movie movie) -> movie.getMpaaRating() == rating)
                    .collect(Collectors.toList());
        } finally {
            dataLock.unlock();
        }
    }

    // dataLock must be locked
    private void removeElement(Integer id) throws SQLException {
        Server.universalStatement.executeQuery("delete from movies where id=" + id);
        Server.universalStatement.executeQuery("delete from owners where id=" + id);
        if (data.get(id).hasPassportID()) {
            knownPassportIDs.remove(data.get(id).getDirector().getPassportID());
        }
        data.remove(id);
        owners.remove(id);
    }

    private int generateNewId() throws SQLException {
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
