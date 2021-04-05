package io.github.caesiumfox.lab05.command;

import io.github.caesiumfox.lab05.Database;
import io.github.caesiumfox.lab05.element.Movie;
import io.github.caesiumfox.lab05.exceptions.*;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Команда удаления меньших записей
 */
public class RemoveLower extends Command {
    private Movie inputEntry = null;
    public RemoveLower(ArrayList<String> args, Database database,
                PrintStream output, Scanner input) {
        super(args, database, output, input);
    }

    @Override
    protected void prepare() throws InvalidArgumentsException {
        try {
            if (args.size() > 1) {
                if (args.get(1).equals("--entry")) {
                    inputEntry = new Movie(args, 2);
                }
            }
        } catch (StringLengthLimitationException | CoordinatesOutOfRangeException |
                NumberOutOfRangeException | WrongEnumInputException |
                IndexOutOfBoundsException e) {
            throw new InvalidArgumentsException(args,
                    "Something went wrong during the initialization of inline entry");
        }
    }

    @Override
    protected void execute() {
        if(inputEntry == null)
            inputEntry = new Movie(0, output, input, database);
        database.remove_lower(inputEntry);
    }

    @Override
    public void getHelp() {
        output.println("Command: remove_lower");
        output.println("Usage:   remove_lower");
        output.println("  Launches an interactive builder that asks for each field of the test element,\n" +
                "  then removes all elements less that the test element. The fields are asked in\n" +
                "  the same order as listed by \"info\" command. You can also define the element by\n" +
                "  command arguments preceded by \"--entry\" key. The order is listed above.");
    }
}
