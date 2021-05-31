package io.github.caesiumfox.lab06.common.exceptions;

public class WrongKeyWordCodeException extends Exception {
    public WrongKeyWordCodeException(byte code) {
        super(hexOfByte(code) + " is not a valid key word kode code");
    }

    private static String hexOfByte(byte val) {
        char[] result = new char[2];
        int high = (val >>> 4) & 0xF, low = val & 0xF;

        if(high < 10)
            result[0] = (char)('0' + high);
        else
            result[0] = (char)('A' + high - 10);

        if(low < 10)
            result[1] = (char)('0' + low);
        else
            result[1] = (char) ('A' + low - 10);

        return new String(result);
    }
}
