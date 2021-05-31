package io.github.caesiumfox.lab06.common.entry;

import io.github.caesiumfox.lab06.common.exceptions.StringLengthLimitationException;

import java.io.Serializable;
import java.util.Objects;

/**
 * Класс, описывающий человека.
 */
public class Person {
    /**
     * Вспомогательный класс для работы с JSON файлами
     * при помощи библиотеки GSON.
     * GSON работает напосредственно с ним,
     * преобразование между ним и
     * основным классом ({@link Person}) происходит
     * отдельно.
     */
    public static class RawData implements Serializable  {
        public String name;
        public String passportID; // may be null
        public Color hairColor;
    }

    private String name;
    private String passportID;
    private Color hairColor;

    /**
     * Наименьшая длина номера паспорта
     */
    public static int passportIDMinLen = 6;
    /**
     * Наибольшая длина номера паспорта
     */
    public static int passportIDMaxLen = 46;

    /**
     * Конструктор по умолчанию.
     * Создаёт человека с зелёными волосами,
     * именем "a" и отсутствующим паспортным.
     */
    public Person() {
        name = "a";
        passportID = null;
        hairColor = Color.GREEN;
    }

    /**
     * Конструктор, инициализирующий человека
     * с заданными параметрами.
     *
     * @param name       Имя человека
     * @param passportID Номер пасспорта
     * @param hairColor  Цвет волос
     * @throws StringLengthLimitationException Если
     *                                         имя пустое или номер паспорта выходит за пределы промежутка
     *                                         [{@link #passportIDMinLen}, {@link #passportIDMaxLen}]
     */
    public Person(String name, String passportID, Color hairColor) throws StringLengthLimitationException {
        setName(name);
        setPassportID(passportID);
        setHairColor(hairColor);
    }

    /**
     * Конструктор, инициализирующий человека
     * в соответствии с первичными данными,
     * полученными в результате чтения json файла
     *
     * @param rawData Объект класса {@link RawData},
     *                содержащий данные из json файла
     * @throws StringLengthLimitationException Если
     *                                         имя пустое или номер паспорта выходит за пределы промежутка
     *                                         [{@link #passportIDMinLen}, {@link #passportIDMaxLen}]
     */
    public Person(RawData rawData) throws StringLengthLimitationException {
        setName(rawData.name);
        setPassportID(rawData.passportID);
        setHairColor(rawData.hairColor);
    }

    /**
     * Возвращает имя человека
     *
     * @return Имя
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает номер пасспорта
     *
     * @return Номер пасспорта (может быть null)
     */
    public String getPassportID() {
        return passportID;
    }

    /**
     * Возвращает цвет волос
     *
     * @return Цвет волос
     */
    public Color getHairColor() {
        return hairColor;
    }

    /**
     * Устанавливает имя человека
     *
     * @param name Имя человека
     * @throws StringLengthLimitationException Если
     *                                         имя было пустым
     * @throws NullPointerException            Если в качестве
     *                                         параметра было передано значение null
     */
    public void setName(String name) throws StringLengthLimitationException {
        Objects.requireNonNull(name);
        if (name.length() <= 0 || name.length() > 1000) {
            throw new StringLengthLimitationException(name, 1, 1000);
        }
        this.name = name;
    }

    /**
     * Устанавливает номер паспорта
     *
     * @param passportID Номер паспорта (может быть null)
     * @throws StringLengthLimitationException Если
     *                                         номер паспорта выходит за пределы промежутка
     *                                         [{@link #passportIDMinLen}, {@link #passportIDMaxLen}].
     */
    public void setPassportID(String passportID) throws StringLengthLimitationException {
        if (passportID == null) {
            this.passportID = null;
            return;
        }
        if (passportID.length() < passportIDMinLen
                || passportID.length() > passportIDMaxLen) {
            throw new StringLengthLimitationException(passportID,
                    passportIDMinLen, passportIDMaxLen);
        }
        this.passportID = passportID;
    }

    /**
     * Устанавливает цвет волос.
     *
     * @param hairColor Цвет волос
     * @throws NullPointerException Если в качестве
     *                              параметра было передано значение null
     */
    public void setHairColor(Color hairColor) {
        Objects.requireNonNull(hairColor);
        this.hairColor = hairColor;
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
        rawData.name = this.name;
        rawData.passportID = this.passportID; // may be null
        rawData.hairColor = this.hairColor;
        return rawData;
    }
}
