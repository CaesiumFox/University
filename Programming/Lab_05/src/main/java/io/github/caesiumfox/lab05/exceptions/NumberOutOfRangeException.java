package io.github.caesiumfox.lab05.exceptions;

public class NumberOutOfRangeException extends Exception {
    public NumberOutOfRangeException(long value, long minVal, long maxVal) {
        super("The value " + String.valueOf(value) + "is out of range [" +
                String.valueOf(minVal) + ", " +
                (maxVal < 0 ? "infinity" : String.valueOf(maxVal)) + "]");
    }
}
