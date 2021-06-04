package io.github.caesiumfox.lab07.client.command;

import io.github.caesiumfox.lab07.client.Client;
import io.github.caesiumfox.lab07.common.Database;
import io.github.caesiumfox.lab07.common.exceptions.CommandExecutionException;
import io.github.caesiumfox.lab07.common.exceptions.ShellSignalException;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Show extends Command {
    public Show(ArrayList<String> args, Database database,
                PrintStream output, Scanner input) {
        super(args, database, output, input);
    }

    @Override
    protected void prepare() {
    }

    @Override
    protected void execute() throws ShellSignalException, CommandExecutionException, IOException {
        database.show(output);
    }

    @Override
    public void getHelp() {
        if (Client.formattedTerminal) {
            output.println("Command: \u001b[1mshow\u001b[0m");
            output.println("Usage:   \u001b[1mshow\u001b[0m");
            output.println("  Prints the contents of the database. The fields of each entry are printed in\n" +
                    "  the same order as in the output of \u001b[1minfo\u001b[0m command.");
        } else {
            output.println("Command: show");
            output.println("Usage:   show");
            output.println("  Prints the contents of the database. The fields of each entry are printed in\n" +
                    "  the same order as in the output of \"info\" command.");
        }
    }
}
