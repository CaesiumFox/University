package io.github.caesiumfox.lab06.client;

import io.github.caesiumfox.lab06.common.entry.Movie;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

public class Client {
    public static String dateFormat;
    private static Scanner input;

    static {
        dateFormat = "dd.MM.yyyy";
        input = new Scanner(System.in);
        Movie.setDateFormat(dateFormat);
    }

    public static void main(String[] args) {
        System.out.println("Starting Client");
        NetworkManager.init(input);
        try {
            DatabaseManager databaseManager = new DatabaseManager();
            CommandShell shell = new CommandShell(databaseManager);
        } catch (Exception e) {
            System.out.println("Something wrong happened:");
            System.out.println(e.getMessage());
        }
    }
}
