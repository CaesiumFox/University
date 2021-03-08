package io.github.caesiumfox.lab05;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.caesiumfox.lab05.exceptions.EnvVariableNotDefinedException;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static final String envVariableForInputFileName = "LAB_INPUT_FILE";
    public static String dateFormat = "dd.MM.yyyy";
    public static Gson parser;

    private static Database database;
    private static CommandShell shell;
    private static BufferedReader reader;
    private static PrintWriter writer;
    private static String inputFile;

    public static void main(String[] args) {
        parser = new GsonBuilder().setDateFormat(dateFormat).create();
        try {
            inputFile = System.getenv().get(envVariableForInputFileName);
            if(inputFile == null)
                throw new EnvVariableNotDefinedException(envVariableForInputFileName);
            reader = new BufferedReader(new FileReader(inputFile));
            Database.Skeleton skeleton = parser.fromJson(reader, Database.Skeleton.class);
            database = new Database(skeleton, inputFile);
        } catch (FileNotFoundException | EnvVariableNotDefinedException e) {
            System.err.println(e.getMessage());
            Database.Skeleton skeleton = new Database.Skeleton();
            skeleton.creationDate = new Date();
            skeleton.data = new ArrayList<>();
            database = new Database(skeleton, "");
        }
        shell = new CommandShell(database);
        shell.run();
    }
    public static void writeToFile(String data, String fileName) {
        try {
            writer = new PrintWriter(new FileWriter(fileName));
            writer.println(data);
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("Aborted writing");
        }
    }
}
