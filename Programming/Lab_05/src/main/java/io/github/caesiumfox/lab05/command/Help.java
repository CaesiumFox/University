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
        // TODO
        output.println("help [command]");
        output.println("      - ");
        output.println("info");
        output.println("      - ");
        output.println("show");
        output.println("      - ");
        output.println("insert [key]");
        output.println("      - ");
        output.println("update <key>");
        output.println("      - ");
        output.println("remove_key <key>");
        output.println("      - ");
        output.println("clear");
        output.println("      - ");
        output.println("save <filename>");
        output.println("      - ");
        output.println("execute_script <filename>");
        output.println("      - ");
        output.println("exit");
        output.println("      - ");
        output.println("remove_lower");
        output.println("      - ");
        output.println("remove_greater_key <key>");
        output.println("      - ");
        output.println("remove_lower_key <key>");
        output.println("      - ");
        output.println("min_by_mpaa_rating");
        output.println("      - ");
        output.println("count_greater_than_oscars_count <count>");
        output.println("      - ");
        output.println("filter_by_mpaa_rating <rating>");
        output.println("      - ");
    }

    @Override
    public void getHelp() {
        output.println("Command: help");
        output.println("Usage:   help [command]");
        output.println("  Prints the brief description about each command or the specified command.");
    }
}
