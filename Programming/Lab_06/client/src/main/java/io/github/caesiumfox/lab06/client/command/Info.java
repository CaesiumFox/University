package io.github.caesiumfox.lab06.client.command;

import io.github.caesiumfox.lab06.common.Database;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Команда информации
 */
public class Info extends Command {
    public Info(ArrayList<String> args, Database database,
                PrintStream output, Scanner input) {
        super(args, database, output, input);
    }

    @Override
    protected void prepare() {}

    @Override
    protected void execute() {
        database.info(output);
    }

    @Override
    public void getHelp() {
        output.println("Command: info");
        output.println("Usage:   info");
        output.println("  Prints the information about the database including input file, creation date,\n" +
                "  number of entries, maximum ID, type of the collection used in the database and\n" +
                "  all fields");
    }
}
