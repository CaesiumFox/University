package io.github.caesiumfox.lab05.command;

import io.github.caesiumfox.lab05.Database;
import io.github.caesiumfox.lab05.exceptions.CommandExecutionException;
import io.github.caesiumfox.lab05.exceptions.InvalidCommandException;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Help extends Command {
    private String command;
    public Help(ArrayList<String> args, Database database,
                PrintStream output, PrintStream errout, Scanner input) {
        super(args, database, output, errout, input);
    }

    @Override
    protected void prepare() {
        if(args.size() > 1) {
            command = args.get(1);
        } else {
            command = "";
        }
    }

    @Override
    protected void execute() throws CommandExecutionException {
        if (!command.isEmpty()) {
            try {
                ArrayList<String> subArg = new ArrayList<>();
                subArg.add(args.get(1));
                Command.getCommand(subArg, database, output, errout, input).getHelp();
            } catch(InvalidCommandException e) {
                throw new CommandExecutionException(e);
            }
            return;
        }

        output.println("help [command]");
        output.println("      - show the help for commands");
        output.println("info");
        output.println("      - print information about the database");
        output.println("show");
        output.println("      - print all database elements");
        output.println("insert [key]");
        output.println("      - add a new element");
        output.println("update <key>");
        output.println("      - replace an existing element");
        output.println("remove_key <key>");
        output.println("      - remove an existing element by key");
        output.println("clear");
        output.println("      - clear the database");
        output.println("save <filename>");
        output.println("      - save the database in the file");
        output.println("execute_script <filename>");
        output.println("      - run script");
        output.println("exit");
        output.println("      - exit this program");
        output.println("remove_lower");
        output.println("      - remove all elements less than the specified");
        output.println("remove_greater_key <key>");
        output.println("      - remove all elements with IDs greater that specified");
        output.println("remove_lower_key <key>");
        output.println("      - remove all elements with IDs less that specified");
        output.println("min_by_mpaa_rating");
        output.println("      - print the element with least MPAA rating");
        output.println("count_greater_than_oscars_count <count>");
        output.println("      - print the number of elements with more oscars than specified");
        output.println("filter_by_mpaa_rating <rating>");
        output.println("      - print all elements with the specified MPAA rating");
    }

    @Override
    public void getHelp() {
        output.println("Command: help");
        output.println("Usage:   help [command]");
        output.println("  Prints the brief description about each command or the specified command.");
    }
}
