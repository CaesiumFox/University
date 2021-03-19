package io.github.caesiumfox.lab05.command;

import io.github.caesiumfox.lab05.CommandShell;
import io.github.caesiumfox.lab05.Database;
import io.github.caesiumfox.lab05.exceptions.CommandExecutionException;
import io.github.caesiumfox.lab05.exceptions.InvalidArgumentsException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class ExecuteScript extends Command {
    private String scriptFilename;

    public ExecuteScript(ArrayList<String> args, Database database,
                PrintStream output, PrintStream errout, Scanner input) {
        super(args, database, output, errout, input);
    }

    @Override
    protected void prepare() throws InvalidArgumentsException {
        if(args.size() < 2) {
            throw new InvalidArgumentsException(args);
        }
        scriptFilename = args.get(1);
    }

    @Override
    protected void execute() throws CommandExecutionException {
        try {
            FileReader reader = new FileReader(scriptFilename);
            CommandShell childShell = new CommandShell(database, reader);
            childShell.run();
        } catch (FileNotFoundException e) {
            throw new CommandExecutionException(e);
        }
    }

    @Override
    public void getHelp() {
        output.println("Command: execute_script");
        output.println("Usage:   execute_script <filename>");
        output.println("  Runs the script. the script must contain only commands listed by \"help\"\n" +
                "  command. The script may contain another \"execute_script\" command but recursive\n" +
                "  execution is prohibited.");
    }
}
