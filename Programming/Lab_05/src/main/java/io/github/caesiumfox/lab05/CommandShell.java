package io.github.caesiumfox.lab05;

import io.github.caesiumfox.lab05.command.Command;
import io.github.caesiumfox.lab05.exceptions.InvalidCommandException;
import io.github.caesiumfox.lab05.exceptions.ShellSignalException;
import io.github.caesiumfox.lab05.exceptions.ShellSignalExitException;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class CommandShell {
    private Database database;
    private boolean running;
    private Scanner input;
    private PrintStream output;
    private PrintStream errout;

    public CommandShell(Database database) {
        setDatabase(database);
        input = new Scanner(System.in);
        output = System.out;
        errout = System.err;
        running = false;
    }

    public Database getDatabase() {
        return database;
    }
    public void setDatabase(Database database) {
        Objects.requireNonNull(database);
        this.database = database;
    }

    public void run() {
        running = true;
        while(running) {
            try {
                String line = input.nextLine();
                ArrayList<String> args = new ArrayList<String>(Arrays.asList(line.split(" ")));
                Command command = Command.getCommand(args, database, output, input);
                command.run();
            } catch (InvalidCommandException e) {
                errout.println(e.getMessage());
            } catch (ShellSignalExitException e) {
                output.println(e.getMessage());
                System.exit(0);
            } catch (ShellSignalException e) {
                output.println(e.getMessage());
            }
        }
    }
}
