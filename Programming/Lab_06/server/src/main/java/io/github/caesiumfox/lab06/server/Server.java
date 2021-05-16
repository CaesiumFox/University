package io.github.caesiumfox.lab06.server;

import io.github.caesiumfox.lab06.common.entry.*;

import javax.print.attribute.standard.DateTimeAtCompleted;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Server {
    public static Logger logger;

	public static String dateFormat;
	private static Scanner input;

    static {
        dateFormat = "dd.MM.yyyy";
        input = new Scanner(System.in);
        Movie.setDateFormat(dateFormat);

        logger = Logger.getLogger(Server.class.getName());
    }

    public static void main(String[] args) {
        System.out.println("Starting Server");
        logger.info("Starting Server");
        NetworkManager.init(input);

        try {
            DatabaseManager databaseManager = new DatabaseManager();
            CommandManager commandManager = new CommandManager(databaseManager);
            commandManager.run();
        } catch (Exception e) {
            System.out.println("Something wrong happened:");
            System.out.println(e.getMessage());
            Server.logger.severe("Error: " + e.getMessage());
        }
    }
}
