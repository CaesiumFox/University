package io.github.caesiumfox.lab06.client;

import io.github.caesiumfox.lab06.common.entry.Movie;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    public static String dateFormat;
    private static Scanner input;
    private static DatabaseManager databaseManager;
    private static CommandShell shell;

    static {
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
