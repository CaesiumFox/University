package io.github.caesiumfox.lab06.client.command;

import io.github.caesiumfox.lab06.common.Database;
import io.github.caesiumfox.lab06.common.exceptions.*;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Базовый класс для всех команд командного
 * интерфейса программы
 */
public abstract class Command {
    /**
     * Поток вывода для команды
     */
    protected PrintStream output;
    /**
     * Поток ввода для команды
     */
    protected Scanner input;
    /**
     * Ссылка на базу данных
     */
    protected Database database;
    /**
     * Список аргументов команды включая
     * саму команду как первый аргумент
     */
    protected ArrayList<String> args;

    /**
     * Обработка аргументов команды.
     * @throws InvalidArgumentsException Если
     * аргументы не допустимы для команды
     */
    protected abstract void prepare() throws InvalidArgumentsException;

    /**
     * Выполнение команды после обработки аргументов.
     * @throws ShellSignalException Отправка сигнала
     * командной оболочке
     * @throws CommandExecutionException Если
     * произошла неполадка при выполнении команды
     */
    protected abstract void execute() throws ShellSignalException, CommandExecutionException;

    /**
     * Запуск команды
     * @throws ShellSignalException Отправка сигнала
     * командной оболочке
     * @throws CommandExecutionException Если
     * произошла неполадка при выполнении команды
     * @throws InvalidArgumentsException Если
     * аргументы не допустимы для команды
     */
    public final void run() throws ShellSignalException,
            CommandExecutionException, InvalidArgumentsException {
        prepare();
        execute();
    }

    /**
     * Выводит в поток вывода справку по команде
     */
    public abstract void getHelp();

    /**
     * Конструктор, инициализирующий общие поля команды
     * @param args Аргументы, включая саму команду
     * @param database Ссылка на базу данных
     * @param output Поток вывода команды
     * @param input Поток ввода команды
     */
    public Command(ArrayList<String> args, Database database,
                   PrintStream output, Scanner input) {
        this.args = args;
        this.database = database;
        this.output = output;
        this.input = input;
    }

    /**
     * В соответствии с именем команды создаёт
     * один из наследников класса {@link Command}.
     * Имя команды берётся как первый элемент
     * списка аргументов.
     * @param args Аргументы, включая саму команду
     * @param database Ссылка на базу данных
     * @param output Поток вывода команды
     * @param input Поток ввода команды
     * @return Объект наследника класса {@link Command}.
     * @throws InvalidCommandException Если
     * не существует команды с указанным именем
     */
    public static Command getCommand(ArrayList<String> args, Database database,
                                     PrintStream output, Scanner input)
            throws InvalidCommandException {
        switch(args.get(0)) {
            case "help":
                return new Help(args, database, output, input);
            case "info":
                return new Info(args, database, output, input);
            case "show":
                return new Show(args, database, output, input);
            case "insert":
                return new Insert(args, database, output, input);
            case "update":
                return new Update(args, database, output, input);
            case "remove_key":
                return new RemoveKey(args, database, output, input);
            case "clear":
                return new Clear(args, database, output, input);
            case "save":
                return new Save(args, database, output, input);
            case "execute_script":
                return new ExecuteScript(args, database, output, input);
            case "exit":
                return new Exit(args, database, output, input);
            case "remove_lower":
                return new RemoveLower(args, database, output, input);
            case "remove_greater_key":
                return new RemoveGreaterKey(args, database, output, input);
            case "remove_lower_key":
                return new RemoveLowerKey(args, database, output, input);
            case "min_by_mpaa_rating":
                return new MinByMpaaRating(args, database, output, input);
            case "count_greater_than_oscars_count":
                return new CountGreaterThanOscarsCount(args, database, output, input);
            case "filter_by_mpaa_rating":
                return new FilterByMpaaRating(args, database, output, input);
        }
        throw new InvalidCommandException(args.get(0));
    }

    public PrintStream getOutput() {
        return output;
    }
    public Scanner getInput() {
        return input;
    }
    public Database getDatabase() {
        return database;
    }
    public ArrayList<String> getArgs() {
        return args;
    }

    public void setOutput(PrintStream output) {
        this.output = output;
    }
    public void setInput(Scanner input) {
        this.input = input;
    }
    public void setDatabase(Database database) {
        this.database = database;
    }

    /**
     * Возвращает перечисленные через пробел
     * команду и её аргументы.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < args.size(); i++) {
            if(i > 0)
                result.append(' ');
            if(args.get(i).contains(" ")) {
                result.append('"');
                result.append(args.get(i));
                result.append('"');
            } else {
                result.append(args.get(i));
            }
        }
        return result.toString();
    }
}
