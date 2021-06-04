package io.github.caesiumfox.lab07.client.command;

import io.github.caesiumfox.lab07.client.Client;
import io.github.caesiumfox.lab07.common.Database;
import io.github.caesiumfox.lab07.common.entry.Movie;
import io.github.caesiumfox.lab07.common.exceptions.*;

import java.io.IOException;
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
    protected void execute() throws ShellSignalException, CommandExecutionException, IOException {
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
        if (Client.formattedTerminal) {
            output.println("Command: \u001b[1mupdate\u001b[0m");
            output.println("Usage:   \u001b[1mupdate\u001b[0m \u001b[36m<id>\u001b[0m " +
                    "\u001b[36m[--entry \u001b[33m<movie name> <x> <y> <oscars> <genre> <rating>\u001b[36m\n" +
                    "         [\u001b[33m<director's name>\u001b[36m [passport ID] \u001b[33m<hair color>\u001b[36m]]\u001b[0m");
            output.println("  Replaces the current element by the specified key with the new element. The\n" +
                    "  creation date is also updated automatically. To create the new element the\n" +
                    "  command launches an interactive builder that asks for each field of the new\n" +
                    "  element. The fields are asked in the same order as listed by \u001b[1minfo\u001b[0m command.\n" +
                    "  You can also define the element by command arguments preceded by \u001b[36m--entry\u001b[0m\n" +
                    "  key. The order is listed above.");
        } else {
            output.println("Command: update");
            output.println("Usage:   update <id> [--entry <movie name> <x> <y> <oscars> <genre> <rating>\n" +
                    "         [<director's name> [passport ID] <hair color>]]");
            output.println("  Replaces the current element by the specified key with the new element. The\n" +
                    "  creation date is also updated automatically. To create the new element the\n" +
                    "  command launches an interactive builder that asks for each field of the new\n" +
                    "  element. The fields are asked in the same order as listed by \"info\" command.\n" +
                    "  You can also define the element by command arguments preceded by \"--entry\"\n" +
                    "  key. The order is listed above.");
        }
    }
}
