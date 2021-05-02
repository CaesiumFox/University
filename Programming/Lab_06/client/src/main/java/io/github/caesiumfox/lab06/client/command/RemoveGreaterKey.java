package io.github.caesiumfox.lab06.client.command;

import io.github.caesiumfox.lab06.common.Database;
import io.github.caesiumfox.lab06.common.exceptions.*;

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
    protected void execute() throws CommandExecutionException {
        try {
            database.removeGreaterKey(id);
        } catch (NumberOutOfRangeException e) {
            throw new CommandExecutionException(e);
        }
    }

    @Override
    public void getHelp() {
        output.println("Command: remove_greater_key");
        output.println("Usage:   remove_greater_key <id>");
        output.println("  Removes all entries with the key greater than the specified.");
    }
}
