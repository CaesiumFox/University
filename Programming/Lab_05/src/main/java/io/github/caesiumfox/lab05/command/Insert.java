package io.github.caesiumfox.lab05.command;

import io.github.caesiumfox.lab05.Database;
import io.github.caesiumfox.lab05.element.Movie;
import io.github.caesiumfox.lab05.exceptions.*;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Команда вставки
 */
public class Insert extends Command {
    private Integer id;
    public Insert(ArrayList<String> args, Database database,
                  PrintStream output, Scanner input) {
        super(args, database, output, input);
    }

    @Override
    protected void prepare() throws InvalidArgumentsException {
        try {
            if (args.size() > 1)
                id = Integer.parseInt(args.get(1));
            else
                id = null; // Is checked for null
        } catch (NumberFormatException e) {
            throw new InvalidArgumentsException(args);
        }
        if (id != null && id <= 0)
            throw new InvalidArgumentsException(args, "ID must be a natural value");
    }

    @Override
    protected void execute() throws CommandExecutionException {
        try {
            if (id == null) {
                database.insert(new Movie(id, output, input, database));
            } else {
                database.insert(id, new Movie(id, output, input, database));
            }
        } catch(RunOutOfIdsException | ElementIdAlreadyExistsException |
                PassportIdAlreadyExistsException | NumberOutOfRangeException e) {
            throw new CommandExecutionException(e);
        }
    }

    @Override
    public void getHelp() {
        output.println("Command: insert");
        output.println("Usage:   insert [id]");
        output.println("  Inserts an entry in the database with the specified ID or the least unused ID,\n" +
                "  if not specified. To create the entry the command launches an interactive\n" +
                "  builder that asks for each field of the new element. The fields are asked in\n" +
                "  the same order as listed by \"info\" command.");
    }
}
