package io.github.caesiumfox.lab07.common;

import java.nio.ByteBuffer;

public class Tools {
    public static String readString(ByteBuffer buffer) {
        int length = buffer.getInt();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++)
            builder.append(buffer.getChar());
        return builder.toString();
    }

    public static void writeString(ByteBuffer buffer, String str) {
        buffer.putInt(str.length());
        for (int i = 0; i < str.length(); i++)
            buffer.putChar(str.charAt(i));
    }
}