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
    protected void execute() throws ShellSignalException, CommandExecutionException, IOException {
        database.clear();
    }

    @Override
    public void getHelp() {
        if(Client.formattedTerminal) {
            output.println("Command: \u001b[1mclear\u001b[0m");
            output.println("Usage:   \u001b[1mclear\u001b[0m");
        } else {
            output.println("Command: clear");
            output.println("Usage:   clear");
        }
        output.println("  Removes all entries in the database but keeps the input file and the creation\n" +
                "  date unchanged.");
    }
}
