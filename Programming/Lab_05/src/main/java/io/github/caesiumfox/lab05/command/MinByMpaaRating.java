package io.github.caesiumfox.lab05.command;

import io.github.caesiumfox.lab05.Database;
import io.github.caesiumfox.lab05.exceptions.EmptyDatabaseException;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MinByMpaaRating extends Command {
    public MinByMpaaRating(ArrayList<String> args, Database database,
                PrintStream output, PrintStream errout, Scanner input) {
        super(args, database, output, errout, input);
    }

    @Override
    protected void prepare() {}

    @Override
    protected void execute() {
        try {
            output.println(database.min_by_mpaa_rating());
        } catch (EmptyDatabaseException e) {
            output.println(e.getMessage());
        }
    }

    @Override
    public void getHelp() {
        output.println("Command: info");
        output.println("Usage:   info");
        output.println("  Prints the information about the database.");
    }
}
