package io.github.caesiumfox.lab05.command;

import io.github.caesiumfox.lab05.Database;
import io.github.caesiumfox.lab05.exceptions.CommandExecutionException;
import io.github.caesiumfox.lab05.exceptions.InvalidArgumentsException;
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
    protected abstract void prepare() throws InvalidArgumentsException;
    protected abstract void execute() throws ShellSignalException, CommandExecutionException;
    public void run() throws ShellSignalException,
            CommandExecutionException, InvalidArgumentsException {
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
                return new Help(args, database, output, errout, input);
            case "info":
                return new Info(args, database, output, errout, input);
            case "show":
                return new Show(args, database, output, errout, input);
            case "insert":
                return new Insert(args, database, output, errout, input);
            case "update":
                return new Update(args, database, output, errout, input);
            case "remove_key":
                return new RemoveKey(args, database, output, errout, input);
            case "clear":
                return new Clear(args, database, output, errout, input);
            case "save":
                return new Save(args, database, output, errout, input);
            case "execute_script":
                // TODO
                output.println("WIP command");
            case "exit":
                return new Exit(args, database, output, errout, input);
            case "remove_lower":
                return new RemoveLower(args, database, output, errout, input);
            case "remove_greater_key":
                return new RemoveGreaterKey(args, database, output, errout, input);
            case "remove_lower_key":
                return new RemoveLowerKey(args, database, output, errout, input);
            case "min_by_mpaa_rating":
                return new MinByMpaaRating(args, database, output, errout, input);
            case "count_greater_than_oscars_count":
                return new CountGreaterThanOscarsCount(args, database, output, errout, input);
            case "filter_by_mpaa_rating":
                return new FilterByMpaaRating(args, database, output, errout, input);
        }
        throw new InvalidCommandException(args.get(0));
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
