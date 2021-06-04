package io.github.caesiumfox.lab07.client.command;

import io.github.caesiumfox.lab07.client.Client;
import io.github.caesiumfox.lab07.common.Database;
import io.github.caesiumfox.lab07.common.exceptions.CommandExecutionException;
import io.github.caesiumfox.lab07.common.exceptions.EmptyDatabaseException;
import io.github.caesiumfox.lab07.common.exceptions.ShellSignalException;

import java.io.IOException;
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
    protected void prepare() {
    }

    @Override
    protected void execute() throws ShellSignalException, CommandExecutionException, IOException {
        try {
            if (Client.formattedTerminal)
                output.println(database.minByMpaaRating().toColoredString());
            else
                output.println(database.minByMpaaRating());
        } catch (EmptyDatabaseException e) {
            output.println(e.getMessage());
        }
    }

    @Override
    public void getHelp() {
        if (Client.formattedTerminal) {
            output.println("Command: \u001b[1mmin_by_mpaa_rating\u001b[0m");
            output.println("Usage:   \u001b[1mmin_by_mpaa_rating\u001b[0m");
        } else {
            output.println("Command: min_by_mpaa_rating");
            output.println("Usage:   min_by_mpaa_rating");
        }
        output.println("  Prints the entry with the least MPAA rating, or prints a placeholder if the\n" +
                "  database is empty.");
    }
}
