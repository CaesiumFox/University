package io.github.caesiumfox.lab06.client.command;

import io.github.caesiumfox.lab06.common.Database;
import io.github.caesiumfox.lab06.common.exceptions.EmptyDatabaseException;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Команта вывода записи с минмальной возрастной категорией
 */
public class MinByMpaaRating extends Command {
    public MinByMpaaRating(ArrayList<String> args, Database database,
                PrintStream output, Scanner input) {
        super(args, database, output, input);
    }

    @Override
    protected void prepare() {}

    @Override
    protected void execute() {
        try {
            output.println(database.minByMpaaRating());
        } catch (EmptyDatabaseException e) {
            output.println(e.getMessage());
        }
    }

    @Override
    public void getHelp() {
        output.println("Command: min_by_mpaa_rating");
        output.println("Usage:   min_by_mpaa_rating");
        output.println("  Prints the entry with the least MPAA rating, or prints a placeholder if the\n" +
                "  database is empty.");
    }
}
