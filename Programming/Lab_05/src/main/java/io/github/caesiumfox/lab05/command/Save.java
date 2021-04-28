package io.github.caesiumfox.lab05.command;

import io.github.caesiumfox.lab05.Database;
import io.github.caesiumfox.lab05.exceptions.InvalidArgumentsException;
import io.github.caesiumfox.lab05.exceptions.ShellSignalSaveException;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Команда сохранения
 */
public class Save extends Command {
    private String outputFile;

    public Save(ArrayList<String> args, Database database,
                PrintStream output, Scanner input) {
        super(args, database, output, input);
    }

    @Override
    protected void prepare() throws InvalidArgumentsException {
        if (args.size() > 1)
            outputFile = args.get(1);
        else
            outputFile = database.getInputFile();
    }

    @Override
    protected void execute() throws ShellSignalSaveException {
        throw new ShellSignalSaveException(outputFile);
    }

    @Override
    public void getHelp() {
        output.println("Command: save");
        output.println("Usage:   save [file name]");
        output.println("  Saves the database in the specified file in JSON format.");
    }
}
