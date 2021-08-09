package io.github.caesiumfox.lab07.client;

import io.github.caesiumfox.lab07.common.KeyWord;
import io.github.caesiumfox.lab07.common.entry.Movie;
import sun.nio.ch.Net;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.security.Key;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    public static final boolean formattedTerminal;
    public static String dateFormat;
    private static Scanner input;
    private static DatabaseManager databaseManager;
    private static CommandShell shell;

    private static String username;
    private static String password;

    static {
        String envVariableForFormattedTerminal = "LAB_INPUT_FILE";
        String formattedTerminalStr = System.getenv().get(envVariableForFormattedTerminal);
        if (formattedTerminalStr != null) {
            formattedTerminalStr = formattedTerminalStr.trim().toLowerCase();
            if (formattedTerminalStr.equals("0") || formattedTerminalStr.equals("false")) {
                formattedTerminal = false;
            } else {
                formattedTerminal = true;
            }
        } else {
            formattedTerminal = true;
        }
        dateFormat = "dd.MM.yyyy";
        input = new Scanner(System.in);
        Movie.setDateFormat(dateFormat);
    }

    public static void main(String[] args) {
        System.out.println("Starting Client");

        try {
            NetworkManager.init(input);
        } catch (NoSuchElementException e) {
            System.out.println("Looks like you have entered an EOF character " +
                    "by pressing Ctrl+D.");
            System.out.println("Unfortunately the program can no longer continue working.");
            System.exit(1);
        }

        init();

        try {
            databaseManager = new DatabaseManager();
            shell = new CommandShell(databaseManager);
            shell.run();
        } catch (NoSuchElementException e) {
            System.out.println("Looks like you have entered an EOF character " +
                    "by pressing Ctrl+D.");
            System.out.println("Unfortunately the program can no longer continue working.");
            System.out.println("Try to restart the client");
            System.exit(1);
        } catch (Exception e) {
            System.out.println("Something wrong happened:");
            System.out.println(e.getMessage());
        }
    }

    private static void init() {
        while (true) {
            try {
                System.out.print("Username: "
                        + (Client.formattedTerminal ? "\u001b[1;33m" : ""));
                username = input.nextLine().trim();
                System.out.print("\u001b[m");
                password = new String(System.console().readPassword("Password: "));

                NetworkManager.byteBuffer.clear();
                putLogin(NetworkManager.byteBuffer, false);
                NetworkManager.byteBuffer.flip();
                NetworkManager.send();
                NetworkManager.receive();
                KeyWord response = KeyWord.getKeyWord(NetworkManager.byteBuffer.get());
                if (response == KeyWord.OK) {
                    break;
                } else if (response == KeyWord.ERROR) {
                    int errorLength = NetworkManager.byteBuffer.getInt();
                    StringBuilder errorBuilder = new StringBuilder();
                    for (int i = 0; i < errorLength; i++)
                        errorBuilder.append(NetworkManager.byteBuffer.getChar());
                    System.out.println(errorBuilder.toString());
                } else {
                    System.out.println("Invalid response from the server");
                }
            } catch (IOException e) {
                System.out.println("Something wrong happened during logging in:");
                System.out.println(e.getMessage());
            }
        }
    }

    public static String getUsername() {
        return username;
    }

    public static void putLogin(ByteBuffer buffer, boolean toRun) {
        if (toRun)
            buffer.put(KeyWord.getCode(KeyWord.LOGIN_RUN));
        else
            buffer.put(KeyWord.getCode(KeyWord.LOGIN_CHECK));
        buffer.putInt(username.length());
        for (int i = 0; i < username.length(); i++)
            buffer.putChar(username.charAt(i));
        buffer.putInt(password.length());
        for (int i = 0; i < password.length(); i++)
            buffer.putChar(password.charAt(i));
    }
}
