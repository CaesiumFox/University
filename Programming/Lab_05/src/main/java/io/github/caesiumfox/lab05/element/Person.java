package io.github.caesiumfox.lab05.element;

import io.github.caesiumfox.lab05.exceptions.StringLengthLimitationException;

import java.util.Objects;

public class Person {
    /**
     * Вспомогательный класс для работы с JSON файлами
     * при помощи библиотеки GSON.
     * GSON работает напосредственно с ним,
     * преобразование между "скелетом" и
     * базовым классом (Person) происходит
     * отдельно.
     */
    public static class Skeleton {
        public String name;
        public String passportID;
        public Color hairColor;
    }

    /**
     * Не может быть null,
     * Не может быть пустой
     */
    private String name;
    /**
     * Не может быть null,
     * Длина от 6 о 46 символов
     * Значение должно быть уникальным
     */
    private String passportID;
    /**
     * Не может быть null
     */
    private Color hairColor;

    public static int passportIDMinLen = 6;
    public static int passportIDMaxLen = 46;


    public Person() {
        name = "";
        passportID = "";
        hairColor = Color.GREEN;
    }
    public Person(String name, String passportID, Color hairColor)  throws StringLengthLimitationException {
        setName(name);
        setPassportID(passportID);
        setHairColor(hairColor);
    }
    public Person(Skeleton skeleton)  throws StringLengthLimitationException {
        setName(skeleton.name);
        setPassportID(skeleton.passportID);
        setHairColor(skeleton.hairColor);
    }

    public String getName() {
        return name;
    }

    public String getPassportID() {
        return passportID;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public void setName(String name) throws StringLengthLimitationException {
        Objects.requireNonNull(name);
        if(name.length() == 0) {
            throw new StringLengthLimitationException(name, 1, -1);
        }
        this.name = name;
    }

    public void setPassportID(String passportID) throws StringLengthLimitationException {
        Objects.requireNonNull(passportID);
        if(passportID.length() < passportIDMinLen
                || passportID.length() > passportIDMaxLen) {
            throw new StringLengthLimitationException(passportID,
                    passportIDMinLen, passportIDMaxLen);
        }
        this.passportID = passportID;
    }

    public void setHairColor(Color hairColor) {
        Objects.requireNonNull(hairColor);
        this.hairColor = hairColor;
    }

    public Skeleton toSkeleton() {
        Skeleton skeleton = new Skeleton();
        skeleton.name = this.name;
        skeleton.passportID = this.passportID;
        skeleton.hairColor = this.hairColor;
        return skeleton;
    }
}
