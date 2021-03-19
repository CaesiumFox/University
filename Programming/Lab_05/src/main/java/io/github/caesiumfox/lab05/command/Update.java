package io.github.caesiumfox.lab05.command;

import io.github.caesiumfox.lab05.Database;
import io.github.caesiumfox.lab05.element.Movie;
import io.github.caesiumfox.lab05.exceptions.*;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Update extends Command {
    private Integer id;

    public Update(ArrayList<String> args, Database database,
                  PrintStream output, PrintStream errout, Scanner input) {
        super(args, database, output, errout, input);
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
    }

    @Override
    protected void execute() throws CommandExecutionException {
        try {
            database.update(id, new Movie(id, output, input, database));
        } catch (NoKeyInDatabaseException | PassportIdAlreadyExistsException e) {
            throw new CommandExecutionException(e);
        }
    }

    @Override
    public void getHelp() {
        output.println("Command: update");
        output.println("Usage:   update <id>");
        output.println("  Replaces the current element by the specified key with the new element. The\n" +
                "  creation date is also updated automatically.\n" +
                "  To create the new element the command launches an interactive builder that\n" +
                "  asks for each field of the new element.");
    }
}
