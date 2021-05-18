package io.github.caesiumfox.lab06.server;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import io.github.caesiumfox.lab06.common.entry.*;
import io.github.caesiumfox.lab06.common.exceptions.*;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Server {
    public static Logger logger;

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
    private static BufferedReader reader;
    private static PrintWriter writer;
    private static String inputFile;
	private static Scanner input;

    static {
        envVariableForInputFileName = "LAB_INPUT_FILE";
        dateFormat = "dd.MM.yyyy";
        input = new Scanner(System.in);
        Movie.setDateFormat(dateFormat);

        logger = Logger.getLogger(Server.class.getName());
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

        try {
            commandManager.run();
        } catch (Exception e) {
            System.out.println("Something wrong happened:");
            System.out.println(e.getMessage());
            Server.logger.severe("Error: " + e.getMessage());
        }
    }
}
