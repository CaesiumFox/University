package io.github.caesiumfox.lab05.command;

import io.github.caesiumfox.lab05.Database;
import io.github.caesiumfox.lab05.exceptions.ShellSignalException;
import io.github.caesiumfox.lab05.exceptions.ShellSignalExitException;

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
    protected void execute() throws ShellSignalException {
        throw new ShellSignalExitException();
    }

    @Override
    public void getHelp() {
        output.println("Command: exit");
        output.println("Usage:   exit");
        output.println("  Exits the program. Nothing is saved automatically.");
    }
}
