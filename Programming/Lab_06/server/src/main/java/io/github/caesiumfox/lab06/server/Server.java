package io.github.caesiumfox.lab06.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import io.github.caesiumfox.lab06.common.entry.*;
import io.github.caesiumfox.lab06.common.exceptions.*;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Server {
    public static Logger logger;
    private static FileHandler loggingFileHandler;

    /**
     * Имя переменной окружения, в которой хранится имя входного файла.
     */
    public static final String envVariableForInputFileName;
    /**
     * Формат дат, используемый при вводе и выводе дат,
     * а также при чтении и записи JSON файлов.
     */
    public static String dateFormat;
    /**
     * JSON парсер.
     */
    public static Gson parser;

    private static DatabaseManager databaseManager;
    private static CommandManager commandManager;
    private static CommandShell commandShell;
    private static BufferedReader reader;
    private static PrintWriter writer;
    private static String inputFile;
	private static Scanner input;

    static {
        envVariableForInputFileName = "LAB_INPUT_FILE";
        dateFormat = "dd.MM.yyyy";
        input = new Scanner(System.in);
        Movie.setDateFormat(dateFormat);

        parser = new GsonBuilder().setDateFormat(dateFormat).create();
        logger = Logger.getLogger(Server.class.getName());
        try {
            loggingFileHandler = new FileHandler("report.log");
            logger.addHandler(loggingFileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            loggingFileHandler.setFormatter(formatter);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            System.out.println("Failed to create/use log file.");
            System.out.println("Logger output is now stdout.");
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting Server");
        logger.info("Starting Server");
        try {
            NetworkManager.init(input);
        } catch (NoSuchElementException e) {
            System.out.println("Looks like you have entered an EOF character " +
                    "by pressing Ctrl+D.");
            System.out.println("Unfortunately the program can no longer continue working.");
            System.exit(1);
        }

        try {
            inputFile = System.getenv().get(envVariableForInputFileName);
            if (inputFile == null)
                throw new EnvVariableNotDefinedException(envVariableForInputFileName);
            reader = new BufferedReader(new FileReader(inputFile));
            DatabaseManager.RawData rawData = parser.fromJson(reader, DatabaseManager.RawData.class);
            databaseManager = new DatabaseManager(rawData, new File(inputFile).getAbsolutePath());
        } catch (FileNotFoundException | EnvVariableNotDefinedException e) {
            System.out.println(e.getMessage());
            System.out.println("An empty database will be initialized");
            databaseManager = new DatabaseManager();
        } catch (NumberOutOfRangeException | StringLengthLimitationException |
                CoordinatesOutOfRangeException | PassportIdAlreadyExistsException |
                ElementIdAlreadyExistsException | NullPointerException |
                JsonParseException e) {
            System.out.println("It looks like the file is corrupted.");
            System.out.println("An exception has been caught with this message:");
            System.out.println(e.getMessage());
            System.out.println("An empty database will be initialized");
            databaseManager = new DatabaseManager();
        } catch (Exception e) {
            System.out.println("Something wrong happened:");
            System.out.println(e.getMessage());
            Server.logger.severe("Error: " + e.getMessage());
        }

        commandManager = new CommandManager(databaseManager);
        commandShell = new CommandShell(input);

        commandManager.setCorrespondingShell(commandShell);
        commandShell.setCorrespondingManager(commandManager);

        commandManager.start();
        commandShell.start();
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
