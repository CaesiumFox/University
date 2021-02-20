package io.github.caesiumfox.lab05.command;

import io.github.caesiumfox.lab05.Database;
import io.github.caesiumfox.lab05.exceptions.InvalidCommandException;
import io.github.caesiumfox.lab05.exceptions.ShellSignalException;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Command {
    protected PrintStream output;
    protected Scanner input;
    protected Database database;
    protected ArrayList<String> args;
    protected abstract void prepare();
    protected abstract void execute() throws ShellSignalException;
    public void run() throws ShellSignalException {
        prepare();
        execute();
    }
    public abstract void getHelp();

    public Command(ArrayList<String> args, Database database,
                   PrintStream output, Scanner input) {
        this.args = args;
        this.database = database;
        this.output = output;
        this.input = input;
    }

    public static Command getCommand(ArrayList<String> args, Database database,
                                     PrintStream output, Scanner input)
            throws InvalidCommandException {
        switch(args.get(0)) {
            case "info":
                return new Info(args, database, output, input);
            case "show":
                return new Show(args, database, output, input);
            case "exit":
                return new Exit(args, database, output, input);
            default:
                throw new InvalidCommandException(args.get(0));
        }
    }

    public PrintStream getOutput() {
        return output;
    }
    public Scanner getInput() {
        return input;
    }
    public Database getDatabase() {
        return database;
    }
    public ArrayList<String> getArgs() {
        return args;
    }

    public void setOutput(PrintStream output) {
        this.output = output;
    }
    public void setInput(Scanner input) {
        this.input = input;
    }
    public void setDatabase(Database database) {
        this.database = database;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(args.get(0));
        for(int i = 1; i < args.size(); i++) {
            result.append(" ").append(args.get(i));
        }
        return result.toString();
    }
}
