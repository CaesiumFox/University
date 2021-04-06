package io.github.caesiumfox.lab05.command;

import io.github.caesiumfox.lab05.Database;
import io.github.caesiumfox.lab05.element.Movie;
import io.github.caesiumfox.lab05.exceptions.*;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Команда обновления
 */
public class Update extends Command {
    private Integer id;
    private Movie inputEntry = null;

    public Update(ArrayList<String> args, Database database,
                  PrintStream output, Scanner input) {
        super(args, database, output, input);
    }

    @Override
    protected void prepare() throws InvalidArgumentsException {
        try {
            if (args.size() > 1)
                id = Integer.parseInt(args.get(1));
            else
                throw new InvalidArgumentsException(args);
        } catch (NumberFormatException e) {
            throw new InvalidArgumentsException(args);
        }
        if(id <= 0)
            throw new InvalidArgumentsException(args, "ID must be a natural value");

        try {
            if (args.size() > 2) {
                if (args.get(2).equals("--entry")) {
                    inputEntry = new Movie(args, 3);
                }
                else {
                    throw new InvalidArgumentsException(args,
                            "Unknown argument: " + args.get(2));
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
    protected void execute() throws CommandExecutionException {
        try {
            if(!database.hasID(id)) {
                throw new NoKeyInDatabaseException(id);
            }
            if(inputEntry == null)
                inputEntry = new Movie(id, output, input, database);
            database.update(id, inputEntry);
        } catch (NoKeyInDatabaseException | PassportIdAlreadyExistsException |
                NumberOutOfRangeException e) {
            throw new CommandExecutionException(e);
        }
    }

    @Override
    public void getHelp() {
        output.println("Command: update");
        output.println("Usage:   update <id> [--entry <movie name> <x> <y> <oscars> <genre> <rating>\n" +
                "         [<director's name> [pasropt ID] <hair color>]]");
        output.println("  Replaces the current element by the specified key with the new element. The\n" +
                "  creation date is also updated automatically. To create the new element the\n" +
                "  command launches an interactive builder that asks for each field of the new\n" +
                "  element. The fields are asked in the same order as listed by \"info\" command.\n" +
                "  You can also define the element by command arguments preceded by \"--entry\"\n" +
                "  key. The order is listed above.");
    }
}
