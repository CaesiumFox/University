package io.github.caesiumfox.lab05;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static String dateFormat = "dd.MM.yyyy";
    private static Database database;
    private static CommandShell shell;
    private static FileReader fileReader;
    private static BufferedReader reader;
    private static final String inputFile = "rc.json";;
    public static void main(String[] args) {
        try {
            fileReader = new FileReader(inputFile);
            reader = new BufferedReader(fileReader);
            Gson parser = new GsonBuilder().setDateFormat(dateFormat).create();
            Database.Skeleton skeleton = parser.fromJson(reader, Database.Skeleton.class);
            database = new Database(skeleton, inputFile);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            Database.Skeleton skeleton = new Database.Skeleton();
            skeleton.creationDate = new Date();
            skeleton.data = new ArrayList<>();
            database = new Database(skeleton, "");
        }
        shell = new CommandShell(database);
        shell.run();
    }
}
