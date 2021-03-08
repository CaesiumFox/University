package io.github.caesiumfox.lab05.command;

import io.github.caesiumfox.lab05.Database;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Clear extends Command {
    public Clear(ArrayList<String> args, Database database,
                PrintStream output, PrintStream errout, Scanner input) {
        super(args, database, output, errout, input);
    }

    @Override
    protected void prepare() {}

    @Override
    protected void execute() {
        database.clear();
    }

    @Override
    public void getHelp() {
        output.println("Command: clear");
        output.println("Usage:   clear");
        output.println("  Clears the database.");
    }
}
