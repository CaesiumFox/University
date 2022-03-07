package io.github.caesiumfox.web4;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

public class PasswordManager {
    private static String letters;
    private static Random random;
    static {
        letters = "qwertyuiopasdfghjklzxcvbnm" +
                  "QWERTYUIOPASDFGHJKLZXCVBNM" +
                  "1234567890" +
                  "-_=+[]{};:,.<>/?|`~!@#$%^&*()";
        random = new Random(System.nanoTime());
    }

    public static String hashPassword(String password, String salt) {
        String sum = password + salt;
        byte[] hash;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            hash = messageDigest.digest(sum.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            hash = new byte[32];
            Arrays.fill(hash, (byte)0);
            byte[] strBytes = sum.getBytes(StandardCharsets.UTF_8);
            for (int i = 0; i < hash.length && i < strBytes.length; i++) {
                hash[i] = strBytes[i];
            }
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            builder.append(toHex(hash[i] & 0xf));
            builder.append(toHex((hash[i] >> 4) & 0xf));
        }
        return builder.toString();
    }

    public static String generateSalt() {
        StringBuilder salt = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            salt.append(letters.charAt(random.nextInt(letters.length())));
        }
        return salt.toString();
    }

    private static char toHex(int digit) {
        if (digit < 0 || digit > 15)
            throw new IllegalArgumentException("Digit " + digit + "is out of range [0, 15]");
        return "0123456789abcdef".charAt(digit);
    }
}
