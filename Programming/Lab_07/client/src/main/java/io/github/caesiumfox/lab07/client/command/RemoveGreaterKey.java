package io.github.caesiumfox.lab07.client.command;

import io.github.caesiumfox.lab07.client.Client;
import io.github.caesiumfox.lab07.common.Database;
import io.github.caesiumfox.lab07.common.exceptions.CommandExecutionException;
import io.github.caesiumfox.lab07.common.exceptions.InvalidArgumentsException;
import io.github.caesiumfox.lab07.common.exceptions.NumberOutOfRangeException;
import io.github.caesiumfox.lab07.common.exceptions.ShellSignalException;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Команда удаления по большему ключу
 */
public class RemoveGreaterKey extends Command {
    private Integer id;

    public RemoveGreaterKey(ArrayList<String> args, Database database,
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
            throw new InvalidArgumentsException(args);
    }

    @Override
    protected void execute() throws ShellSignalException, CommandExecutionException, IOException {
        try {
            database.removeGreaterKey(id);
        } catch (NumberOutOfRangeException e) {
            throw new CommandExecutionException(e);
        }
    }

    @Override
    public void getHelp() {
        if(Client.formattedTerminal) {
            output.println("Command: \u001b[1mremove_greater_key\u001b[0m");
            output.println("Usage:   \u001b[1mremove_greater_key\u001b[0m \u001b[33m<id>\u001b[0m");
        } else {
            output.println("Command: remove_greater_key");
            output.println("Usage:   remove_greater_key <id>");
        }
        output.println("  Removes all entries with the key greater than the specified.");
    }
}
