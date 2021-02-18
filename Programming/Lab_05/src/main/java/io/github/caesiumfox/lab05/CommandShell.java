package io.github.caesiumfox.lab05;

import io.github.caesiumfox.lab05.command.Command;

import java.io.PrintStream;
import java.util.Scanner;

public class CommandShell {
    private Database database;
    private boolean running;
    private Scanner input;
    private PrintStream output;

    public CommandShell(Database database) {
        setDatabase(database);
        input = new Scanner(System.in);
        output = System.out;
        running = false;
    }

    public Database getDatabase() {
        return database;
    }
    public void setDatabase(Database database) {
        if(database == null) {
            throw new NullPointerException();
        }
        this.database = database;
    }

    public void run() {
        running = true;
        while(running) {
        }
    }
}
