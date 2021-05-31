package io.github.caesiumfox.lab06.client.command;

import io.github.caesiumfox.lab06.client.Client;
import io.github.caesiumfox.lab06.common.Database;
import io.github.caesiumfox.lab06.common.exceptions.*;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Команда справки
 */
public class Help extends Command {
    private String command;
    public Help(ArrayList<String> args, Database database,
                PrintStream output, Scanner input) {
        super(args, database, output, input);
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
    protected void execute() throws ShellSignalException, CommandExecutionException, IOException {
        if (!command.isEmpty()) {
            try {
                ArrayList<String> subArg = new ArrayList<>();
                subArg.add(args.get(1));
                Command.getCommand(subArg, database, output, input).getHelp();
            } catch (InvalidCommandException e) {
                throw new CommandExecutionException(e);
            }
            return;
        }

        if (Client.formattedTerminal) {
            output.println("    \u001b[1;4mHelp\u001b[0m    ");
            output.println("\u001b[1mhelp\u001b[0m \u001b[36m[command]\u001b[0m");
            output.println("      - show the help for commands");
            output.println("\u001b[1minfo\u001b[0m");
            output.println("      - print information about the database");
            output.println("\u001b[1mshow\u001b[0m");
            output.println("      - print all database entries");
            output.println("\u001b[1minsert\u001b[0m \u001b[36m[key]\u001b[0m \u001b[36m[--entry ...]\u001b[0m");
            output.println("      - add a new entry");
            output.println("\u001b[1mupdate\u001b[0m \u001b[33m<key>\u001b[0m \u001b[36m[--entry ...]\u001b[0m");
            output.println("      - replace an existing entry");
            output.println("\u001b[1mremove_key\u001b[0m \u001b[33m<key>\u001b[0m");
            output.println("      - remove an existing entry by key");
            output.println("\u001b[1mclear\u001b[0m");
            output.println("      - clear the database");
            output.println("\u001b[1mexecute_script\u001b[0m \u001b[33m<filename>\u001b[0m");
            output.println("      - run script");
            output.println("\u001b[1mexit\u001b[0m");
            output.println("      - exit program");
            output.println("\u001b[1mremove_lower\u001b[0m \u001b[36m[--entry ...]\u001b[0m");
            output.println("      - remove all entries less than the specified");
            output.println("\u001b[1mremove_greater_key\u001b[0m \u001b[33m<key>\u001b[0m");
            output.println("      - remove all entries with IDs greater that specified");
            output.println("\u001b[1mremove_lower_key\u001b[0m \u001b[33m<key>\u001b[0m");
            output.println("      - remove all entries with IDs less that specified");
            output.println("\u001b[1mmin_by_mpaa_rating\u001b[0m");
            output.println("      - print the entry with the least MPAA rating");
            output.println("\u001b[1mcount_greater_than_oscars_count\u001b[0m \u001b[33m<count>\u001b[0m");
            output.println("      - print the number of entries with more oscars than specified");
            output.println("\u001b[1mfilter_by_mpaa_rating\u001b[0m \u001b[33m<rating>\u001b[0m");
            output.println("      - print all entries with the specified MPAA rating");
        } else {
            output.println("--- Help ---");
            output.println("help [command]");
            output.println("      - show the help for commands");
            output.println("info");
            output.println("      - print information about the database");
            output.println("show");
            output.println("      - print all database entries");
            output.println("insert [key] [--entry ...]");
            output.println("      - add a new entry");
            output.println("update <key> [--entry ...]");
            output.println("      - replace an existing entry");
            output.println("remove_key <key>");
            output.println("      - remove an existing entry by key");
            output.println("clear");
            output.println("      - clear the database");
            output.println("execute_script <filename>");
            output.println("      - run script");
            output.println("exit");
            output.println("      - exit program");
            output.println("remove_lower [--entry ...]");
            output.println("      - remove all entries less than the specified");
            output.println("remove_greater_key <key>");
            output.println("      - remove all entries with IDs greater that specified");
            output.println("remove_lower_key <key>");
            output.println("      - remove all entries with IDs less that specified");
            output.println("min_by_mpaa_rating");
            output.println("      - print the entry with the least MPAA rating");
            output.println("count_greater_than_oscars_count <count>");
            output.println("      - print the number of entries with more oscars than specified");
            output.println("filter_by_mpaa_rating <rating>");
            output.println("      - print all entries with the specified MPAA rating");
        }
    }

    @Override
    public void getHelp() {
        if(Client.formattedTerminal) {
            output.println("Command: \u001b[1mhelp\u001b[0m");
            output.println("Usage:   \u001b[1mhelp\u001b[0m \u001b[36m[command]\u001b[0m");
        } else {
            output.println("Command: help");
            output.println("Usage:   help [command]");
        }
        output.println("  Prints the brief description about each command or the specified command.");
    }
}
