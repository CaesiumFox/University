package io.github.caesiumfox.lab05.command;

import io.github.caesiumfox.lab05.Database;
import io.github.caesiumfox.lab05.exceptions.CommandExecutionException;
import io.github.caesiumfox.lab05.exceptions.InvalidArgumentsException;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class RemoveLowerKey extends Command {
    private Integer id;

    public RemoveLowerKey(ArrayList<String> args, Database database,
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
        database.remove_lower_key(id);
    }

    @Override
    public void getHelp() {
        output.println("Command: remove_lower_key");
        output.println("Usage:   remove_lower_key <id>");
        output.println("  Removes all the elements that have the key less than the specified.");
    }
}
