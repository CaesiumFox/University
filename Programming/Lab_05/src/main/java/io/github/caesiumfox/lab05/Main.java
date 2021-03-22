package io.github.caesiumfox.lab05;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.caesiumfox.lab05.exceptions.EnvVariableNotDefinedException;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;

/**
 * Главных класс программы.
 * Содержит точку входа.
 */
public class Main {
    /**
     * Имя переменной окружения, в которой хранится имя входного файла.
     */
    public static final String envVariableForInputFileName = "LAB_INPUT_FILE";
    /**
     * Формат дат, используемый при вводе и выводе дат,
     * а также при чтении и записи JSON файлов.
     */
    public static String dateFormat = "dd.MM.yyyy";
    /**
     * JSON парсер.
     */
    public static Gson parser;

    private static Database database;
    private static CommandShell shell;
    private static BufferedReader reader;
    private static PrintWriter writer;
    private static String inputFile;

    /**
     * Точка входа.
     * Инициализирует парсер, базу данных и командную оболочку.
     * Запускает командную оболочку.
     */
    public static void main(String[] args) {
        parser = new GsonBuilder().setDateFormat(dateFormat).create();
        try {
            inputFile = System.getenv().get(envVariableForInputFileName);
            if (inputFile == null)
                throw new EnvVariableNotDefinedException(envVariableForInputFileName);
            reader = new BufferedReader(new FileReader(inputFile));
            Database.RawData rawData = parser.fromJson(reader, Database.RawData.class);
            database = new Database(rawData, new File(inputFile).getAbsolutePath());
        } catch (FileNotFoundException | EnvVariableNotDefinedException e) {
            System.err.println(e.getMessage());
            System.out.println("An empty database will be initialized");
            Database.RawData rawData = new Database.RawData();
            rawData.creationDate = new Date();
            rawData.data = new ArrayList<>();
            database = new Database(rawData, "");
        }
        shell = new CommandShell(database);
        try {
            shell.run();
        } catch (NoSuchElementException e) {
            String backupName;
            if(database.getInputFile().length() > 0) {
                backupName = database.getInputFile() + ".bak";
            } else {
                backupName = "tmp.bak";
            }

            System.out.println("Looks like you have entered an EOF character" +
                    "by pressing Ctrl+D.");
            System.out.println("Unfortunately the program can no longer continue working.");
            System.out.println("The current state of the database will be saved in " + backupName);
            writeToFile(parser.toJson(database.toRawData()), backupName);
            System.exit(1);
        }
    }
    
    /**
     * Записывает строку в файл по его имени.
     * @param data Строка, которая может также содержать
     * переносы строк, которая будет записана в файл
     * @param fileName Имя файла, в который производится запись
     */
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
