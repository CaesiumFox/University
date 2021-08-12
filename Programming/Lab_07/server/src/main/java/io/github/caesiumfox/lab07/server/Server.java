package io.github.caesiumfox.lab07.server;

import io.github.caesiumfox.lab07.common.entry.Movie;

import java.io.*;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Server {
    public static volatile boolean running;

    public static Logger logger;
    private static FileHandler loggingFileHandler;

    public static String dateFormat;

    public static Connection connection;
    private static String adminUser;
    private static String databaseUrl;

	private final static Scanner input;

	static NetworkManager networkManager;
	static SessionController sessionController;
	static DatabaseManager database;
	static Shell shell;

    static {
        input = new Scanner(System.in);
        Movie.setDateFormat(dateFormat);

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
        Movie.setDateFormat("dd.MM.yyyy");
    }

    public static void main(String[] args) {
        System.out.println("Starting Server");
        logger.info("Starting Server");
        try {
            networkManager = new NetworkManager(input);
        } catch (NoSuchElementException e) {
            System.out.println("Looks like you have entered an EOF character " +
                    "by pressing Ctrl+D.");
            System.out.println("Unfortunately the program can no longer continue working.");
            System.exit(1);
        }

        try  {
            System.out.println("Enter database URL:");
            // jdbc:postgresql://localhost:5432/studs
            databaseUrl = input.nextLine().trim();
            System.out.println("Enter username:");
            adminUser = input.nextLine().trim();
            System.out.println("Enter password:");
            String password = new String(System.console().readPassword());
            connection = java.sql.DriverManager.getConnection(databaseUrl, adminUser, password);

            database = new DatabaseManager();
            sessionController = new SessionController();
            shell = new Shell(input);

            running = true;
            networkManager.start();
            sessionController.start();
            shell.start();

            System.out.println("Successfully started server.");
        } catch (SQLException e) {
            System.out.println("An exception has been caught with this message:");
            System.out.println(e.getMessage());
            System.out.println("Looks like the database isn't set properly.");
            System.out.println("Make sure the database follows these requirements:");
            System.out.println("  - the name of the database is 'studs'");
            System.out.println("  - it has a table called 'users' with the following fields:");
            System.out.println("    * Username varchar(64) not null primary key,");
            System.out.println("    * PWHashStr char(32) null,");
            System.out.println("    * Salt varchar(64) null");
            System.out.println("  - it has a table called 'movies' with the following fields:");
            System.out.println("    * ID integer not null primary key,");
            System.out.println("    * Name varchar(1000) not null,");
            System.out.println("    * X real not null,");
            System.out.println("    * Y real not null,");
            System.out.println("    * CreationDate date not null,");
            System.out.println("    * Oscars bigint not null,");
            System.out.println("    * Genre integer not null,");
            System.out.println("    * Rating integer not null,");
            System.out.println("    * DirName varchar(1000) null,");
            System.out.println("    * DirPassport varchar(46) null unique,");
            System.out.println("    * DirHairCol integer null");
            System.out.println("  - it has a table called 'owners' with the following fields:");
            System.out.println("    * Username varchar(64) not null,");
            System.out.println("    * MovieID integer not null,");
            System.out.println("    * primary key (Username, MovieID)");
            System.out.println("  - it has a table called 'meta' with one field and entry:");
            System.out.println("    * CreationDate date not null");
            System.out.println("  - it has a sequence called 'IntID'");
            System.out.println();
            System.exit(2);
        } catch (Exception e) {
            System.out.println("Something wrong happened:");
            System.out.println(e.getMessage());
            Server.logger.severe("Error: " + e.getMessage());
            System.exit(-1);
        }

        try {
            sessionController.join();
            System.out.println("Joined sessionController");
            networkManager.join();
            System.out.println("Joined networkManager");
            shell.join();
            System.out.println("Joined shell");
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String readString(ByteBuffer buffer) {
        int length = buffer.getInt();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++)
            builder.append(buffer.getChar());
        return builder.toString();
    }

    public static void writeString(ByteBuffer buffer, String str) {
        buffer.putInt(str.length());
        for (int i = 0; i < str.length(); i++)
            buffer.putChar(str.charAt(i));
    }

    public static synchronized void stop() {
        running = false;
    }
}
