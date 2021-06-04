package io.github.caesiumfox.lab07.client.command;

import io.github.caesiumfox.lab07.client.Client;
import io.github.caesiumfox.lab07.common.Database;
import io.github.caesiumfox.lab07.common.exceptions.CommandExecutionException;
import io.github.caesiumfox.lab07.common.exceptions.ShellSignalException;
import io.github.caesiumfox.lab07.common.exceptions.ShellSignalExitException;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Команда выхода из программы
 */
public class Exit extends Command {
    public Exit(ArrayList<String> args, Database database,
                PrintStream output, Scanner input) {
        super(args, database, output, input);
    }

    @Override
    protected void prepare() {}

    @Override
    protected void execute() throws ShellSignalException, CommandExecutionException, IOException {
        throw new ShellSignalExitException();
    }

    @Override
    public void getHelp() {
        if(Client.formattedTerminal) {
            output.println("Command: \u001b[1mexit\u001b[0m");
            output.println("Usage:   \u001b[1mexit\u001b[0m");
        } else {
            output.println("Command: exit");
            output.println("Usage:   exit");
        }
        output.println("  Exits the client program.");
    }
}
