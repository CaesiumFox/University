package io.github.caesiumfox.lab05.command;

import io.github.caesiumfox.lab05.Database;
import io.github.caesiumfox.lab05.exceptions.InvalidCommandException;
import io.github.caesiumfox.lab05.exceptions.ShellSignalException;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Command {
    protected PrintStream output;
    protected PrintStream errout;
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
                   PrintStream output, PrintStream errout, Scanner input) {
        this.args = args;
        this.database = database;
        this.output = output;
        this.errout = errout;
        this.input = input;
    }

    public static Command getCommand(ArrayList<String> args, Database database,
                                     PrintStream output, PrintStream errout,
                                     Scanner input)
            throws InvalidCommandException {
        switch(args.get(0)) {
            case "help":
                // TODO
            case "info":
                return new Info(args, database, output, errout, input);
            case "show":
                return new Show(args, database, output, errout, input);
            case "insert":
                return new Insert(args, database, output, errout, input);
            case "update":
                // TODO
            case "remove_key":
                // TODO
            case "clear":
                // TODO
            case "save":
                // TODO
            case "execute_script":
                // TODO
            case "exit":
                return new Exit(args, database, output, errout, input);
            case "remove_lower":
                // TODO
            case "remove_greater_key":
                // TODO
            case "remove_lower_key":
                // TODO
            case "min_by_mpaa_rating":
                // TODO
            case "count_greater_than_oscars_count":
                // TODO
            case "filter_by_mpaa_rating":
                // TODO
            default:
                throw new InvalidCommandException(args.get(0));
        }
    }

    public PrintStream getOutput() {
        return output;
    }
    public PrintStream getErrout() {
        return errout;
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
    public void setErrout(PrintStream errout) {
        this.errout = errout;
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
