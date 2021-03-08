package io.github.caesiumfox.lab05;

import io.github.caesiumfox.lab05.command.Command;
import io.github.caesiumfox.lab05.exceptions.*;

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
        while (running) {
            try {
                output.print("> ");
                String line = input.nextLine();
                ArrayList<String> args = new ArrayList<String>(Arrays.asList(line.split(" ")));
                Command command = Command.getCommand(args, database, output, errout, input);
                command.run();
            } catch (InvalidCommandException | InvalidArgumentsException |
                    CommandExecutionException e) {
                errout.println(e.getMessage());
            } catch (ShellSignalExitException e) {
                output.println(e.getMessage());
                System.exit(0);
            } catch (ShellSignalSaveException e) {
                output.println(e.getMessage());
                Database.Skeleton skel = database.toSkeleton();
                String newJson = Main.parser.toJson(skel);
                Main.writeToFile(newJson, e.getOutputFile());
            } catch (ShellSignalException e) {
                output.println(e.getMessage());
            }
            errout.flush();
            output.flush();
        }
    }
}
