package io.github.caesiumfox.lab07.client;

import io.github.caesiumfox.lab07.common.KeyWord;
import io.github.caesiumfox.lab07.common.Tools;
import io.github.caesiumfox.lab07.common.entry.Movie;

import java.io.IOException;
import java.nio.ByteBuffer;
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
            login();
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
            System.exit(1);
        }
    }

    private static void login() {
        while (true) {
            try {
                System.out.print("Username: ");
                if (Client.formattedTerminal) System.out.print("\u001b[1;33m");
                username = input.nextLine().trim();
                if(Client.formattedTerminal) System.out.print("\u001b[m");
                password = new String(System.console().readPassword("Password: "));

                initiateBuffer(NetworkManager.byteBuffer, 0L);
                NetworkManager.byteBuffer.put(KeyWord.getCode(KeyWord.LOGIN_CHECK));
                NetworkManager.byteBuffer.flip();
                NetworkManager.send();
                NetworkManager.receive();
                NetworkManager.byteBuffer.getLong(); // ignore session
                KeyWord response = KeyWord.getKeyWord(NetworkManager.byteBuffer.get());
                if (response == KeyWord.OK) {
                    break;
                } else if (response == KeyWord.ERROR) {
                    int errorLength = NetworkManager.byteBuffer.getInt();
                    StringBuilder errorBuilder = new StringBuilder();
                    for (int i = 0; i < errorLength; i++)
                        errorBuilder.append(NetworkManager.byteBuffer.getChar());
                    System.out.println(errorBuilder.toString());
                } else if (response == KeyWord.LOGIN_INCORRECT) {
                    if (formattedTerminal)
                        System.out.println("\u001b[1;31mLogin incorrect\u001b[m");
                    else
                        System.out.println("Login incorrect");
                } else {
                    System.out.println("Invalid response from the server: " + response.toString());
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

    public static void initiateBuffer(ByteBuffer buffer, long session) {
        buffer.clear();
        writeString(buffer, username);
        writeString(buffer, password);
        buffer.putLong(session);
    }

    public static String readString(ByteBuffer buffer) {
        return Tools.readString(buffer);
    }

    public static void writeString(ByteBuffer buffer, String str) {
        Tools.writeString(buffer, str);
    }
}
