package io.github.caesiumfox.lab05;

import io.github.caesiumfox.lab05.command.Command;
import io.github.caesiumfox.lab05.exceptions.*;

import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class CommandShell {
    private boolean scripted;
    private Database database;
    private boolean running;
    private Scanner commandInput;
    private Scanner input;
    private PrintStream output;
    private PrintStream errout;

    public CommandShell(Database database) {
        setDatabase(database);
        commandInput = new Scanner(System.in);
        input = commandInput;
        output = System.out;
        errout = System.out;
        running = false;
        scripted = false;
    }
    public CommandShell(Database database, FileReader reader) {
        setDatabase(database);
        commandInput = new Scanner(reader);
        input = new Scanner(System.in);
        output = System.out;
        errout = System.out;
        running = false;
        this.scripted = true;
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
            if(scripted) {
                if(!commandInput.hasNext()) {
                    running = false;
                    break;
                }
            }
            try {
                if(!scripted) {
                    errout.flush();
                    output.print("> ");
                    output.flush();
                }
                String line = commandInput.nextLine().trim();
                if(line.isBlank() || line.charAt(0) == '#')
                    continue;
                ArrayList<String> args = new ArrayList<String>(Arrays.asList(line.split(" ")));
                Command command = Command.getCommand(args, database, output, errout, input);
                command.run();
            } catch (InvalidCommandException | InvalidArgumentsException |
                    CommandExecutionException e) {
                errout.println(e.getMessage());
            } catch (ShellSignalExitException e) {
                if(!scripted) {
                    output.println(e.getMessage());
                    System.exit(0);
                }
            } catch (ShellSignalSaveException e) {
                if(!scripted)
                    output.println(e.getMessage());
                Database.Skeleton skel = database.toSkeleton();
                String newJson = Main.parser.toJson(skel);
                Main.writeToFile(newJson, e.getOutputFile());
            } catch (ShellSignalException e) {
                if(!scripted)
                    output.println(e.getMessage());
            }
            errout.flush();
            if(!scripted)
                output.flush();
        }
    }
}
