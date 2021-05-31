package io.github.caesiumfox.lab06.client.command;

import io.github.caesiumfox.lab06.client.Client;
import io.github.caesiumfox.lab06.common.Database;
import io.github.caesiumfox.lab06.common.exceptions.CommandExecutionException;
import io.github.caesiumfox.lab06.common.exceptions.InvalidArgumentsException;
import io.github.caesiumfox.lab06.common.exceptions.ShellSignalException;

import java.io.IOException;
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
    protected void execute() throws ShellSignalException, CommandExecutionException, IOException {
        output.println(database.countGreaterThanOscarsCount(oscarsCount));
    }

    @Override
    public void getHelp() {
        if(Client.formattedTerminal) {
            output.println("Command: \u001b[1mcount_greater_than_oscars_count\u001b[0m");
            output.println("Usage:   \u001b[1mcount_greater_than_oscars_count\u001b[0m \u001b[33m<count>\u001b[0m");
        } else {
            output.println("Command: count_greater_than_oscars_count");
            output.println("Usage:   count_greater_than_oscars_count <count>");
        }
        output.println("  Prints the number of entries for which the oscars count is greater that the\n" +
                "  specified number. Prints 0 if the database is empty or if there are no such\n" +
                "  entries.");
    }
}
