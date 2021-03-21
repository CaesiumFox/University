package io.github.caesiumfox.lab05;

import io.github.caesiumfox.lab05.command.Command;
import io.github.caesiumfox.lab05.exceptions.*;

import java.io.FileReader;
import java.io.PrintStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandShell {
    private boolean scripted;
    private Database database;
    private boolean running;
    private Scanner commandInput;
    private Scanner input;
    private PrintStream output;

    /**
     * Содержит полные пути к исполняемым скриптам.
     */
    public static Set<String> executingScripts;
    static {
        executingScripts = new HashSet<>();
    }

    public CommandShell(Database database) {
        setDatabase(database);
        commandInput = new Scanner(System.in);
        input = commandInput;
        output = System.out;
        running = false;
        scripted = false;
    }
    public CommandShell(Database database, FileReader reader) {
        setDatabase(database);
        commandInput = new Scanner(reader);
        input = new Scanner(System.in);
        output = System.out;
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
                    output.print("> ");
                    output.flush();
                }
                String line = commandInput.nextLine().trim();
                if(line.isBlank() || line.charAt(0) == '#')
                    continue;
                Pattern pattern = Pattern.compile("\"[^\"]+\"|[^ \"]+");
                Matcher matcher = pattern.matcher(line);
                ArrayList<String> args = new ArrayList<String>();
                while(matcher.find()) {
                    if(line.charAt(matcher.start()) == '"') {
                        args.add(line.substring(matcher.start() + 1, matcher.end() - 1));
                    } else {
                        args.add(line.substring(matcher.start(), matcher.end()));
                    }
                }
                Command command = Command.getCommand(args, database, output, input);
                command.run();
            } catch (InvalidCommandException | InvalidArgumentsException |
                    CommandExecutionException e) {
                output.println(e.getMessage());
            } catch (ShellSignalExitException e) {
                if(!scripted) {
                    output.println(e.getMessage());
                    System.exit(0);
                }
            } catch (ShellSignalSaveException e) {
                if(!scripted)
                    output.println(e.getMessage());
                Database.RawData skel = database.toRawData();
                String newJson = Main.parser.toJson(skel);
                Main.writeToFile(newJson, e.getOutputFile());
            } catch (ShellSignalException e) {
                if(!scripted)
                    output.println(e.getMessage());
            }
            if(!scripted)
                output.flush();
        }
    }
}
