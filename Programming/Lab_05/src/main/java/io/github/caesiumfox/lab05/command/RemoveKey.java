package io.github.caesiumfox.lab05.command;

import io.github.caesiumfox.lab05.Database;
import io.github.caesiumfox.lab05.exceptions.CommandExecutionException;
import io.github.caesiumfox.lab05.exceptions.InvalidArgumentsException;
import io.github.caesiumfox.lab05.exceptions.NoKeyInDatabaseException;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class RemoveKey extends Command {
    private Integer id;

    public RemoveKey(ArrayList<String> args, Database database,
                     PrintStream output, PrintStream errout, Scanner input) {
        super(args, database, output, errout, input);
    }

    @Override
    protected void prepare() throws InvalidArgumentsException {
        if (args.size() > 1)
            id = Integer.parseInt(args.get(1));
        else
            throw new InvalidArgumentsException(args);
    }

    @Override
    protected void execute() throws CommandExecutionException {
        try {
            database.remove_key(id);
        } catch (NoKeyInDatabaseException e) {
            throw new CommandExecutionException(e);
        }
    }

    @Override
    public void getHelp() {
        output.println("Command: remove_key");
        output.println("Usage:   remove_key <id>");
        output.println("  Removes the element by the specified key.");
    }
}
