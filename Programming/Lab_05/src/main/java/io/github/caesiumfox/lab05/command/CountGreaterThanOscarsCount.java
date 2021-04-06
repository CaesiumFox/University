package io.github.caesiumfox.lab05.command;

import io.github.caesiumfox.lab05.Database;
import io.github.caesiumfox.lab05.exceptions.InvalidArgumentsException;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Команда подсчёта записей с количеством оскаров
 * больше заданного значения
 */
public class CountGreaterThanOscarsCount extends Command {
    private long oscarsCount;
    public CountGreaterThanOscarsCount(ArrayList<String> args, Database database,
                  PrintStream output, Scanner input) {
        super(args, database, output, input);
    }

    @Override
    protected void prepare() throws InvalidArgumentsException {
        try {
            if (args.size() > 1)
                oscarsCount = Long.parseLong(args.get(1));
            else
                throw new InvalidArgumentsException(args);
        } catch (NumberFormatException e) {
            throw new InvalidArgumentsException(args);
        }
    }

    @Override
    protected void execute() {
        output.println(database.countGreaterThanOscarsCount(oscarsCount));
    }

    @Override
    public void getHelp() {
        output.println("Command: count_greater_than_oscars_count");
        output.println("Usage:   count_greater_than_oscars_count <count>");
        output.println("  Prints the number of entries for which the oscars count is greater that the\n" +
                "  specified number. Prints 0 if the database is empty or if there are no such\n" +
                "  entries.");
    }
}
