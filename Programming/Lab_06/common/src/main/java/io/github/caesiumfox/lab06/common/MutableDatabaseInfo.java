package io.github.caesiumfox.lab06.common;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Содержит ту часть данных о базе данных,
 * которая может изменяться со временем.
 */
public class MutableDatabaseInfo implements Serializable {
    private Date creationDate;
    private int maxID;
    private int numberOfElements;
    private String inputFile;

    public void putInByteBuffer(ByteBuffer output) {
        output.putLong(creationDate.getTime());
        output.putInt(maxID);
        output.putInt(numberOfElements);
        output.putInt(inputFile.length());
        for(int i = 0; i < inputFile.length(); i++) {
            output.putChar(inputFile.charAt(i));
        }
    }

    public void getFromByteBuffer(ByteBuffer input) {
        creationDate = new Date();
        creationDate.setTime(input.getLong());
        maxID = input.getInt();
        numberOfElements = input.getInt();
        StringBuilder inputFileBuilder = new StringBuilder();
        int inputFileLength = input.getInt();
        for(int i = 0; i < inputFileLength; i++) {
            inputFileBuilder.append(input.getChar());
        }
        inputFile = inputFileBuilder.toString();
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setMaxID(int maxID) {
        this.maxID = maxID;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public int getMaxID() {
        return maxID;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public String getInputFile() {
        return inputFile;
    }
}
