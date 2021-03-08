package io.github.caesiumfox.lab05.command;

import io.github.caesiumfox.lab05.Database;
import io.github.caesiumfox.lab05.element.Movie;
import io.github.caesiumfox.lab05.exceptions.CommandExecutionException;
import io.github.caesiumfox.lab05.exceptions.ElementIdAlreadyExistsException;
import io.github.caesiumfox.lab05.exceptions.PassportIdAlreadyExistsException;
import io.github.caesiumfox.lab05.exceptions.RunOutOfIdsException;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Insert extends Command {
    private Integer id;
    public Insert(ArrayList<String> args, Database database,
                  PrintStream output, PrintStream errout, Scanner input) {
        super(args, database, output, errout, input);
    }

    @Override
    protected void prepare() {
        if(args.size() > 1)
            id = Integer.parseInt(args.get(1));
        else
            id = null; // Is checked for null
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
                PassportIdAlreadyExistsException e) {
            throw new CommandExecutionException(e);
        }
    }

    @Override
    public void getHelp() {
        output.println("Command: insert");
        output.println("Usage:   insert [id]");
        output.println("  Inserts an element in the database with the specified ID or the least unused\n" +
                "  ID, if not specified.\n" +
                "  To create the element the command launches an interactive builder that asks\n" +
                "  for each field of the new element.");
    }
}
