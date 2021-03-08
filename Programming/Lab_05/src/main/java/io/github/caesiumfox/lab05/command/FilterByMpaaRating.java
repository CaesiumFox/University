package io.github.caesiumfox.lab05.command;

import io.github.caesiumfox.lab05.Database;
import io.github.caesiumfox.lab05.element.Movie;
import io.github.caesiumfox.lab05.element.MpaaRating;
import io.github.caesiumfox.lab05.exceptions.InvalidArgumentsException;
import io.github.caesiumfox.lab05.exceptions.WrongEnumInputException;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class FilterByMpaaRating extends Command {
    private MpaaRating model;

    public FilterByMpaaRating(ArrayList<String> args, Database database,
                PrintStream output, PrintStream errout, Scanner input) {
        super(args, database, output, errout, input);
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
        for(Movie movie : database.filter_by_mpaa_rating(model)) {
            output.println(movie);
        }
    }

    @Override
    public void getHelp() {
        output.println("Command: filter_by_mpaa_rating");
        output.println("Usage:   filter_by_mpaa_rating <rating>");
        output.println("  Prints all elements with the same MPAA rating as specified.");
    }
}
