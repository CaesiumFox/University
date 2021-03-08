package io.github.caesiumfox.lab05.exceptions;

public class StringLengthLimitationException extends Exception {
    public StringLengthLimitationException(String str, int minLen, int maxLen) {
        super("String \"" + str + "\" length is out of range ["
                + String.valueOf(minLen) + ", "
                + (maxLen < 0 ? "infinity" : String.valueOf(maxLen)) + "]");
    }
}
