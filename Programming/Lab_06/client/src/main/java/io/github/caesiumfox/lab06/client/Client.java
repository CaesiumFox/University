package io.github.caesiumfox.lab06.client;

import io.github.caesiumfox.lab06.common.entry.Movie;
import io.github.caesiumfox.lab06.common.exceptions.EnvVariableNotDefinedException;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    public static final boolean formattedTerminal;
    public static String dateFormat;
    private static Scanner input;
    private static DatabaseManager databaseManager;
    private static CommandShell shell;

    static {
        String envVariableForFormattedTerminal = "LAB_INPUT_FILE";
        String formattedTerminalStr = System.getenv().get(envVariableForFormattedTerminal);
        if(formattedTerminalStr != null) {
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
}
