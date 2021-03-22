package io.github.caesiumfox.lab05.exceptions;

import java.util.List;

/**
 * Выбрасывается, если команде
 * был передан неправильный набор
 * аргументов.
 */
public class InvalidArgumentsException extends Exception {
    /**
     * Создаёт исключение, которое
     * выбрасывается, если команде
     * был передан неправильный набор
     * аргументов.
     * @param args Аргументы команды
     */
    public InvalidArgumentsException(List<String> args) {
        super(buildMessage(args));
    }

    /**
     * Создаёт исключение, которое
     * выбрасывается, если команде
     * был передан неправильный набор
     * аргументов.
     * @param args Аргументы команды
     * @param additional Дополнительная
     * информация, например, описание
     * правильных аргументов
     */
    public InvalidArgumentsException(List<String> args, String additional) {
        super(buildMessage(args) + "\n" + additional);
    }

    private static String buildMessage(List<String> args) {
        StringBuilder result = new StringBuilder();
        if(args.size() == 1) {
            result.append("Command \"");
            result.append(args.get(0));
            result.append("\" requires arguments");
        } else if(args.size() == 2) {
            result.append("Argument \"");
            result.append(args.get(1));
            result.append("\" is not valid for command \"");
            result.append(args.get(0));
            result.append('"');
        } else {
            result.append("Arguments \"");
            result.append(args.get(1));
            for(int i = 2; i < args.size(); i++) {
                result.append(' ');
                result.append(args.get(i));
            }
            result.append("\" are not valid for command \"");
            result.append(args.get(0));
            result.append("\"");
        }
        return result.toString();
    }
}
