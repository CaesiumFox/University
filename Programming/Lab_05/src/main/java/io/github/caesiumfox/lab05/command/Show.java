package io.github.caesiumfox.lab05.command;

import io.github.caesiumfox.lab05.Database;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Show extends Command {
    public Show(ArrayList<String> args, Database database,
                PrintStream output, Scanner input) {
        super(args, database, output, input);
    }

    @Override
    protected void prepare() {}

    @Override
    protected void execute() {
        database.show(output);
    }

    @Override
    public void getHelp() {
        output.println("Command: show");
        output.println("Usage:   show");
        output.println("  Prints the contents of the database");
    }
}
