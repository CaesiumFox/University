package io.github.caesiumfox.lab06.client;

import io.github.caesiumfox.lab06.client.command.Command;
import io.github.caesiumfox.lab06.common.exceptions.*;

import java.io.FileReader;
import java.io.PrintStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс отвечающий за обработку команд
 * в текстовом виде и их запуск.
 * Работает как в интерактивном, так и
 * в скриптовом режиме.
 */
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

    /**
     * Конструктор, инициализирующий командную оболочку
     * в интерактивном режиме.
     * @param database База данных, с которой будет работать оболочка
     */
    public CommandShell(Database database) {
        setDatabase(database);
        commandInput = new Scanner(System.in);
        input = commandInput;
        output = System.out;
        running = false;
        scripted = false;
    }
    
    /**
     * Конструктор, инициализирующий командную оболочку
     * в скриптовом режиме.
     * @param database База данных, с которой будет работать оболочка
     * @param reader Источник команд
     */
    public CommandShell(Database database, FileReader reader) {
        setDatabase(database);
        commandInput = new Scanner(reader);
        input = new Scanner(System.in);
        output = System.out;
        running = false;
        this.scripted = true;
    }

    /**
     * Возвращает базу данных, с которой работает оболочка.
     * @return База данных, с которой происходит работа
     */
    public Database getDatabase() {
        return database;
    }

    /**
     * Меняет базу данных, с которой будет работать оболочка.
     * @param database Новая база данных
     */
    public void setDatabase(Database database) {
        Objects.requireNonNull(database);
        this.database = database;
    }

    /**
     * Метод, запускающий командную оболочку.
     * Выполнен в виде цикла, выполняющегося
     * до тех пор, пока не будет послан сигнал
     * завершения работы.
     * Метод может выбросить
     * {@link NoSuchElementException},
     * если во время работы были нажаты клавиши
     * Ctrl+D.
     * Если на вход дана пустая строка или
     * строка, начинающаяся с символа '#',
     * командная оболочка её проигнорирует.
     * Для переноса строк нужно в конец поставить
     * символ '\'. Если в месте разрыва нужно поставить
     * пробел, то он ставится перед знаком переноса.
     * Аргументы, содержащие пробелы могут быть заключены
     * в кавычки.
     */
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
                StringBuilder lineBuilder =
                        new StringBuilder(commandInput.nextLine().trim());
                //
                while(lineBuilder.length() == 0 || lineBuilder.charAt(lineBuilder.length() - 1) == '\\') {
                    if(lineBuilder.length() > 0)
                        lineBuilder.deleteCharAt(lineBuilder.length() - 1);
                    lineBuilder.append(commandInput.nextLine().trim());
                }
                //
                String line = lineBuilder.toString();
                if(line.trim().isEmpty() || line.charAt(0) == '#')
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
                if(args.size() == 0) {
                    continue;
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
