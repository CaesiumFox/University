package io.github.caesiumfox.lab06.client.command;

import io.github.caesiumfox.lab06.client.Client;
import io.github.caesiumfox.lab06.common.Database;
import io.github.caesiumfox.lab06.common.entry.*;
import io.github.caesiumfox.lab06.common.exceptions.*;

import java.io.IOException;
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
    protected void execute() throws ShellSignalException, CommandExecutionException, IOException {
        if(inputEntry == null)
            inputEntry = new Movie(0, output, input, database);
        database.removeLower(inputEntry);
    }

    @Override
    public void getHelp() {
        if (Client.formattedTerminal) {
            output.println("Command: \u001b[1mremove_lower\u001b[0m");
            output.println("Usage:   \u001b[1mremove_lower\u001b[0m " +
                    "\u001b[36m[--entry \u001b[33m<movie name> <x> <y> <oscars> <genre> <rating>\u001b[36m\n" +
                    "         [\u001b[33m<director's name>\u001b[36m [passport ID] \u001b[33m<hair color>\u001b[36m]]\u001b[0m");
            output.println("  Launches an interactive builder that asks for each field of the test element,\n" +
                    "  then removes all elements less that the test element. The fields are asked in\n" +
                    "  the same order as listed by \u001b[1minfo\u001b[0m command. You can also define the element by\n" +
                    "  command arguments preceded by \u001b[36m--entry\u001b[0m key. The order is listed above.");
        } else {
            output.println("Command: remove_lower");
            output.println("Usage:   remove_lower [--entry <movie name> <x> <y> <oscars> <genre> <rating>\n" +
                    "         [<director's name> [passport ID] <hair color>]]");
            output.println("  Launches an interactive builder that asks for each field of the test element,\n" +
                    "  then removes all elements less that the test element. The fields are asked in\n" +
                    "  the same order as listed by \"info\" command. You can also define the element by\n" +
                    "  command arguments preceded by \"--entry\" key. The order is listed above.");
        }
    }
}
