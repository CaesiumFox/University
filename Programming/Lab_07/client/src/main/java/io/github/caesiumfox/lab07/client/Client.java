package io.github.caesiumfox.lab07.client;

import io.github.caesiumfox.lab07.common.KeyWord;
import io.github.caesiumfox.lab07.common.Tools;
import io.github.caesiumfox.lab07.common.entry.Movie;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    public static boolean formattedTerminal;
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
        Movie.shortString = true;
        Movie.formatted = formattedTerminal;
    }

    public static void main(String[] args) {
        System.out.println("Starting Client");
        System.out.print("Use SGR? [Y/n]");
        String answer = input.nextLine().trim().toLowerCase();
        formattedTerminal = (answer.isEmpty() || answer.equals("y") || answer.equals("yes"));

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
        if (formattedTerminal)
            System.out.print("Enter \u001b[1;33m[R]\u001b[0m " +
                    "to \u001b[1msign up\u001b[0m, or " +
                    "anything else" +
                    " to \u001b[1msign in\u001b[0m: ");
        else
            System.out.print("Enter [R] to sign up, or anything else to sign up: ");
        boolean register = input.nextLine().trim().toLowerCase().equals("r");
        if (register) {
            while (true) {
                try {
                    System.out.print("New username: ");
                    if (Client.formattedTerminal) System.out.print("\u001b[1;33m");
                    String newUsername = input.nextLine().trim();
                    if (Client.formattedTerminal) System.out.print("\u001b[m");
                    String newPassword = new String(System.console().readPassword("Password: "));

                    initiateBuffer(NetworkManager.byteBuffer, 0L, true);
                    NetworkManager.byteBuffer.put(KeyWord.getCode(KeyWord.REGISTER_USER));
                    writeString(NetworkManager.byteBuffer, newUsername);
                    writeString(NetworkManager.byteBuffer, newPassword);
                    NetworkManager.byteBuffer.flip();
                    NetworkManager.send();
                    NetworkManager.receive();
                    KeyWord response = KeyWord.getKeyWord(NetworkManager.byteBuffer.get());
                    if (response == KeyWord.OK) {
                        username = newUsername;
                        password = newPassword;
                        if (formattedTerminal)
                            System.out.println("\u001b[32mYou have signed up and signed in successfully\u001b[0m");
                        else
                            System.out.println("You have signed up and signed in successfully");
                        break;
                    } else if (response == KeyWord.ERROR) {
                        System.out.println(readString(NetworkManager.byteBuffer));
                    } else if (response == KeyWord.LOGIN_INCORRECT) {
                        if (formattedTerminal)
                            System.out.println("\u001b[1;31mUser " + newUsername + " already exists\u001b[m");
                        else
                            System.out.println("User " + newUsername + " already exists");
                    } else {
                        System.out.println("Invalid response from the server: " + response.toString());
                    }
                } catch (IOException e) {
                    System.out.println("Something wrong happened during registering or logging in:");
                    System.out.println(e.getMessage());
                }
            }
        } else {
            while (true) {
                try {
                    System.out.print("Username: ");
                    if (Client.formattedTerminal) System.out.print("\u001b[1;33m");
                    username = input.nextLine().trim();
                    if (Client.formattedTerminal) System.out.print("\u001b[m");
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
                        System.out.println(readString(NetworkManager.byteBuffer));
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
    }

    public static String getUsername() {
        return username;
    }

    public static void initiateBuffer(ByteBuffer buffer, long session, boolean isSpecial) {
        buffer.clear();
        if (isSpecial) {
            buffer.put(KeyWord.getCode(KeyWord.SPECIAL_QUERY));
        } else {
            buffer.put(KeyWord.getCode(KeyWord.REGULAR_QUERY));
            writeString(buffer, username);
            writeString(buffer, password);
            buffer.putLong(session);
        }
    }
    public static void initiateBuffer(ByteBuffer buffer, long session) {
        initiateBuffer(buffer, session, false);
    }

    public static String readString(ByteBuffer buffer) {
        return Tools.readString(buffer);
    }

    public static void writeString(ByteBuffer buffer, String str) {
        Tools.writeString(buffer, str);
    }
}
