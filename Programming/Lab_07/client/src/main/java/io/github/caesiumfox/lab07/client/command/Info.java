package io.github.caesiumfox.lab07.client.command;

import io.github.caesiumfox.lab07.client.Client;
import io.github.caesiumfox.lab07.common.Database;
import io.github.caesiumfox.lab07.common.exceptions.CommandExecutionException;
import io.github.caesiumfox.lab07.common.exceptions.ShellSignalException;

import java.io.IOException;
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
    protected void execute() throws ShellSignalException, CommandExecutionException, IOException {
        database.info(output);
    }

    @Override
    public void getHelp() {
        if(Client.formattedTerminal) {
            output.println("Command: \u001b[1minfo\u001b[0m");
            output.println("Usage:   \u001b[1minfo\u001b[0m");
        } else {
            output.println("Command: info");
            output.println("Usage:   info");
        }
        output.println("  Prints the information about the database including input file, creation date,\n" +
                "  number of entries, maximum ID, type of the collection used in the database and\n" +
                "  all fields");
    }
}
