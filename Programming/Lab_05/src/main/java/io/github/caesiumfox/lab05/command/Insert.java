package io.github.caesiumfox.lab05.command;

import io.github.caesiumfox.lab05.Database;
import io.github.caesiumfox.lab05.element.Movie;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Insert extends Command {
    private Integer id;
    public Insert(ArrayList<String> args, Database database,
                  PrintStream output, PrintStream errout, Scanner input) {
        super(args, database, errout, output, input);
    }

    @Override
    protected void prepare() {
        if(args.size() > 1) {
            id = Integer.parseInt(args.get(1));
        }
        else {
            id = null;
        }
    }

    @Override
    protected void execute() {
        if(id == null) {
            database.insert(new Movie(id, output, errout, input, database));
        } else {
            database.insert(id, new Movie(id, output, errout, input, database));
        }
    }

    @Override
    public void getHelp() {
        output.println("Command: info");
        output.println("Usage:   info");
        output.println("  Prints the information about the" +
                "  database.");
    }
}
