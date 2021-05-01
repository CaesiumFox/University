package io.github.caesiumfox.lab06.common;

import io.github.caesiumfox.lab06.common.entry.Movie;
import io.github.caesiumfox.lab06.common.entry.MpaaRating;
import io.github.caesiumfox.lab06.common.exceptions.*;

import java.io.PrintStream;
import java.util.Date;
import java.util.Set;

public interface Database {
    public boolean hasPassportID(String passportId);
    public String getInputFile();
    public Date getCreationDate();
    public boolean hasID(Integer id);
    public boolean hasRanOutOfIDs();


    /**
     * Выводит в поток вывода информацию о
     * базе данных
     *
     * @param output Поток вывода
     */
    public void info(PrintStream output);

    /**
     * Выводит в поток вывода информацию о каждой
     * записи в базе данных.
     *
     * @param output Поток вывода
     */
    public void show(PrintStream output);

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
    public void insert(Movie movie) throws RunOutOfIdsException,
            PassportIdAlreadyExistsException, NumberOutOfRangeException;

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
    public void insert(Integer id, Movie movie)
            throws ElementIdAlreadyExistsException, PassportIdAlreadyExistsException,
            NumberOutOfRangeException;

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
    public void update(Integer id, Movie movie)
            throws NoKeyInDatabaseException, PassportIdAlreadyExistsException,
            NumberOutOfRangeException;

    /**
     * Удаляет запись о фильме по его идентификатору (ключу).
     *
     * @param id Идентификатор записи, которую нужно удалить
     * @throws NoKeyInDatabaseException Если нет
     *                                  зфписи с указанным идентификатором
     */
    public void removeKey(Integer id) throws NoKeyInDatabaseException,
            NumberOutOfRangeException;

    /**
     * Очищает базу данных, но сохраняет дату создания
     * и файл-источник.
     */
    public void clear();

    /**
     * Удаляет все записи, которые в соответствии
     * с компаратором {@link MovieComparator}
     * меньшие, чем заданная запись.
     *
     * @param movie Запись, с которой производится сравнение
     */
    public void removeLower(Movie movie);

    /**
     * Удаляет все записи, идентификатор (ключ)
     * которых больше чем заданный.
     *
     * @param id Значение ключа, с которым производится сравнение
     */
    public void removeGreaterKey(Integer id) throws NumberOutOfRangeException;

    /**
     * Удаляет все записи, идентификатор (ключ)
     * которых меньше чем заданный.
     *
     * @param id Значение ключа, с которым производится сравнение
     */
    public void removeLowerKey(Integer id) throws NumberOutOfRangeException;

    /**
     * Возвращает любую запись с наименьшей
     * возрастной категорией
     *
     * @return Запись с наименьшей возрастной категорей
     * @throws EmptyDatabaseException Если база
     *                                данных пуста
     */
    public Movie minByMpaaRating() throws EmptyDatabaseException;

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
    public int countGreaterThanOscarsCount(long oscarsCount);

    /**
     * Возвращает множество всех записей с заданной
     * возрастной категорией. Если таких записей нет
     * или база данных пуста, возвращается пустое множество.
     *
     * @param rating Искомая возрастная категория
     * @return Множество всех записей с заданной возрастной категорией
     */
    public Set<Movie> filterByMpaaRating(MpaaRating rating);
}
