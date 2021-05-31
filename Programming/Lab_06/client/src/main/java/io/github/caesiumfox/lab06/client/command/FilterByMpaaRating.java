package io.github.caesiumfox.lab06.client.command;

import io.github.caesiumfox.lab06.client.Client;
import io.github.caesiumfox.lab06.common.Database;
import io.github.caesiumfox.lab06.common.entry.Movie;
import io.github.caesiumfox.lab06.common.entry.MpaaRating;
import io.github.caesiumfox.lab06.common.exceptions.CommandExecutionException;
import io.github.caesiumfox.lab06.common.exceptions.InvalidArgumentsException;
import io.github.caesiumfox.lab06.common.exceptions.ShellSignalException;
import io.github.caesiumfox.lab06.common.exceptions.WrongEnumInputException;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Команда фильтрации по возрастной категории
 */
public class FilterByMpaaRating extends Command {
    private MpaaRating model;

    public FilterByMpaaRating(ArrayList<String> args, Database database,
                PrintStream output, Scanner input) {
        super(args, database, output, input);
    }

    @Override
    protected void prepare() throws InvalidArgumentsException {
        if(args.size() < 1)
            throw new InvalidArgumentsException(args);
        try {
            model = MpaaRating.fromString(args.get(1));
        } catch(WrongEnumInputException e) {
            throw new InvalidArgumentsException(args, e.getMessage());
        }
    }

    @Override
    protected void execute() throws ShellSignalException, CommandExecutionException, IOException {
        List<Movie> list = database.filterByMpaaRating(model);
        if (list.size() == 0) {
            if(Client.formattedTerminal)
                output.println("  \u001b[3mThere are no such entries\u001b[3m");
            else
                output.println("  There are no such entries");
        } else {
            if(Client.formattedTerminal) {
                for (Movie movie : list) {
                    output.println(movie.toColoredString());
                }
            } else {
                for (Movie movie : list) {
                    output.println(movie);
                }
            }
        }
    }

    @Override
    public void getHelp() {
        if(Client.formattedTerminal) {
            output.println("Command: \u001b[1mfilter_by_mpaa_rating\u001b[0m");
            output.println("Usage:   \u001b[1mfilter_by_mpaa_rating\u001b[0m \u001b[33m<rating>\u001b[0m");
        } else {
            output.println("Command: filter_by_mpaa_rating");
            output.println("Usage:   filter_by_mpaa_rating <rating>");
        }
        output.println("  Prints all entries with the same MPAA rating as specified or prints a\n" +
                "  placeholder if there are no such entries.");
    }
}
