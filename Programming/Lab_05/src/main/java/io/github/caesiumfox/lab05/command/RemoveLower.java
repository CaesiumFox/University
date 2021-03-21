package io.github.caesiumfox.lab05.command;

import io.github.caesiumfox.lab05.Database;
import io.github.caesiumfox.lab05.element.Movie;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Команда удаления меньших записей
 */
public class RemoveLower extends Command {
    public RemoveLower(ArrayList<String> args, Database database,
                PrintStream output, Scanner input) {
        super(args, database, output, input);
    }

    @Override
    protected void prepare() {}

    @Override
    protected void execute() {
        Movie test = new Movie(0, output, input, database);
        database.remove_lower(test);
    }

    @Override
    public void getHelp() {
        output.println("Command: remove_lower");
        output.println("Usage:   remove_lower");
        output.println("  Launches an interactive builder that asks for each field of the test element,\n" +
                "  then removes all elements less that the test element. The fields are asked in\n" +
                "  the same order as listed by \"info\" command.");
    }
}
