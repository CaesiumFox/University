package io.github.caesiumfox.lab06.client.command;

import io.github.caesiumfox.lab06.client.Client;
import io.github.caesiumfox.lab06.common.Database;
import io.github.caesiumfox.lab06.common.exceptions.*;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Команда удаления по ключу
 */
public class RemoveKey extends Command {
    private Integer id;

    public RemoveKey(ArrayList<String> args, Database database,
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
        if (id <= 0)
            throw new InvalidArgumentsException(args, "ID must be a natural value");
    }

    @Override
    protected void execute() throws ShellSignalException, CommandExecutionException, IOException {
        try {
            database.removeKey(id);
        } catch (NoKeyInDatabaseException | NumberOutOfRangeException e) {
            throw new CommandExecutionException(e);
        }
    }

    @Override
    public void getHelp() {
        if (Client.formattedTerminal) {
            output.println("Command: \u001b[1mremove_key\u001b[0m");
            output.println("Usage:   \u001b[1mremove_key\u001b[0m \u001b[33m<id>\u001b[0m");
        } else {
            output.println("Command: remove_key");
            output.println("Usage:   remove_key <id>");
        }
        output.println("  Removes the entry by the specified key (if) if such entry exists.");
    }
}
