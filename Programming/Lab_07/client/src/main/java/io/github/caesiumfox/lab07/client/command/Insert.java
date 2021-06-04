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
 * Команда вставки
 */
public class Insert extends Command {
    private Integer id;
    private Movie inputEntry = null;

    public Insert(ArrayList<String> args, Database database,
                  PrintStream output, Scanner input) {
        super(args, database, output, input);
    }

    @Override
    protected void prepare() throws InvalidArgumentsException {
        int nextArg = 1;
        try {
            if (args.size() > 1 && !args.get(1).equals("--entry")) {
                id = Integer.parseInt(args.get(1));
                nextArg++;
            } else {
                id = null; // Is checked for null
            }
        } catch (NumberFormatException e) {
            throw new InvalidArgumentsException(args);
        }
        if (id != null && id <= 0)
            throw new InvalidArgumentsException(args, "ID must be a natural value");

        try {
            if (args.size() > nextArg) {
                if (args.get(nextArg).equals("--entry")) {
                    inputEntry = new Movie(args, nextArg + 1);
                }
                else {
                    throw new InvalidArgumentsException(args,
                            "Unknown argument: " + args.get(nextArg));
                }
            }
        } catch (StringLengthLimitationException | CoordinatesOutOfRangeException |
                NumberOutOfRangeException | WrongEnumInputException |
                IndexOutOfBoundsException e) {
            throw new InvalidArgumentsException(args,
                    "Something went wrong during the initialization of inline entry: \n"
                    + e.getMessage());
        }
    }

    @Override
    protected void execute() throws ShellSignalException, CommandExecutionException, IOException {
        try {
            if (id == null) {
                if(database.hasRanOutOfIDs()) {
                    throw new RunOutOfIdsException();
                }
                if(inputEntry == null)
                    inputEntry = new Movie(id, output, input, database);
                database.insert(inputEntry);
            } else {
                if(database.hasID(id)) {
                    throw new ElementIdAlreadyExistsException(id);
                }
                if(inputEntry == null)
                    inputEntry = new Movie(id, output, input, database);
                database.insert(id, inputEntry);
            }
        } catch(RunOutOfIdsException | ElementIdAlreadyExistsException |
                PassportIdAlreadyExistsException | NumberOutOfRangeException e) {
            throw new CommandExecutionException(e);
        }
    }

    @Override
    public void getHelp() {
        if (Client.formattedTerminal) {
            output.println("Command: \u001b[1minsert\u001b[0m");
            output.println("Usage:   \u001b[1minsert\u001b[0m \u001b[36m[id]\u001b[0m " +
                    "\u001b[36m[--entry \u001b[33m<movie name> <x> <y> <oscars> <genre> <rating>\u001b[36m\n" +
                    "         [\u001b[33m<director's name>\u001b[36m [passport ID] \u001b[33m<hair color>\u001b[36m]]\u001b[0m");
            output.println("  Inserts an entry in the database with the specified ID or the least unused ID,\n" +
                    "  if not specified. To create the entry the command launches an interactive\n" +
                    "  builder that asks for each field of the new element. The fields are asked in\n" +
                    "  the same order as listed by \u001b[1minfo\u001b[0m command. You can also define the element by\n" +
                    "  command arguments preceded by \u001b[36m--entry\u001b[0m key. The order is listed above.");
        } else {
            output.println("Command: insert");
            output.println("Usage:   insert [id] [--entry <movie name> <x> <y> <oscars> <genre> <rating>\n" +
                    "         [<director's name> [passport ID] <hair color>]]");
            output.println("  Inserts an entry in the database with the specified ID or the least unused ID,\n" +
                    "  if not specified. To create the entry the command launches an interactive\n" +
                    "  builder that asks for each field of the new element. The fields are asked in\n" +
                    "  the same order as listed by \"info\" command. You can also define the element by\n" +
                    "  command arguments preceded by \"--entry\" key. The order is listed above.");
        }
    }
}
