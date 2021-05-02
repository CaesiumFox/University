package io.github.caesiumfox.lab06.client.command;

import io.github.caesiumfox.lab06.common.Database;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Команда очистки базы данных
 */
public class Clear extends Command {
    public Clear(ArrayList<String> args, Database database,
                PrintStream output, Scanner input) {
        super(args, database, output, input);
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
        output.println("  Removes all entries in the database but keeps the input file and the creation\n" +
                "  date unchanged.");
    }
}
