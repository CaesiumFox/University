package io.github.caesiumfox.lab05.command;

import io.github.caesiumfox.lab05.Database;
import io.github.caesiumfox.lab05.element.Movie;
import io.github.caesiumfox.lab05.exceptions.CommandExecutionException;
import io.github.caesiumfox.lab05.exceptions.InvalidArgumentsException;
import io.github.caesiumfox.lab05.exceptions.NoKeyInDatabaseException;
import io.github.caesiumfox.lab05.exceptions.PassportIdAlreadyExistsException;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class CountGreaterThanOscarsCount extends Command {
    private long oscarsCount;
    public CountGreaterThanOscarsCount(ArrayList<String> args, Database database,
                  PrintStream output, PrintStream errout, Scanner input) {
        super(args, database, output, errout, input);
    }

    @Override
    protected void prepare() throws InvalidArgumentsException {
        if(args.size() > 1)
            oscarsCount = Long.parseLong(args.get(1));
        else
            throw new InvalidArgumentsException(args);
    }

    @Override
    protected void execute() {
        output.println(database.count_greater_than_oscars_count(oscarsCount));
    }

    @Override
    public void getHelp() {
        output.println("Command: count_greater_than_oscars_count");
        output.println("Usage:   count_greater_than_oscars_count <count>");
        output.println("  Prints the number of element for which the oscars count is greater that the\n" +
                "  specified number.");
    }
}
