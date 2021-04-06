package io.github.caesiumfox.lab05.command;

import io.github.caesiumfox.lab05.Database;
import io.github.caesiumfox.lab05.exceptions.CommandExecutionException;
import io.github.caesiumfox.lab05.exceptions.InvalidArgumentsException;
import io.github.caesiumfox.lab05.exceptions.NoKeyInDatabaseException;
import io.github.caesiumfox.lab05.exceptions.NumberOutOfRangeException;

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
    protected void execute() throws CommandExecutionException {
        try {
            database.removeKey(id);
        } catch (NoKeyInDatabaseException | NumberOutOfRangeException e) {
            throw new CommandExecutionException(e);
        }
    }

    @Override
    public void getHelp() {
        output.println("Command: remove_key");
        output.println("Usage:   remove_key <id>");
        output.println("  Removes the entry by the specified key if such entry exists.");
    }
}
