package io.github.caesiumfox.lab05.command;

import io.github.caesiumfox.lab05.Database;
import io.github.caesiumfox.lab05.element.Movie;
import io.github.caesiumfox.lab05.element.MpaaRating;
import io.github.caesiumfox.lab05.exceptions.InvalidArgumentsException;
import io.github.caesiumfox.lab05.exceptions.WrongEnumInputException;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

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
    protected void execute() {
        Set<Movie> set = database.filter_by_mpaa_rating(model);
        if (set.size() == 0) {
            output.println("There are no such entries");
        } else {
            for (Movie movie : set) {
                output.println(movie);
            }
        }
    }

    @Override
    public void getHelp() {
        output.println("Command: filter_by_mpaa_rating");
        output.println("Usage:   filter_by_mpaa_rating <rating>");
        output.println("  Prints all entries with the same MPAA rating as specified or prints a\n" +
                "  placeholder if there are no such entries.");
    }
}
