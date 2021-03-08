package io.github.caesiumfox.lab05.command;

import io.github.caesiumfox.lab05.Database;
import io.github.caesiumfox.lab05.exceptions.*;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class RemoveGreaterKey extends Command {
    private Integer id;

    public RemoveGreaterKey(ArrayList<String> args, Database database,
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
        database.remove_greater_key(id);
    }

    @Override
    public void getHelp() {
        output.println("Command: remove_greater_key");
        output.println("Usage:   remove_greater_key <id>");
        output.println("  Removes all the elements that have key greater than the specified.");
    }
}
