package io.github.caesiumfox.lab05.exceptions;

/**
 * Выбрасывается, если переменная
 * окружения, используемая для работы
 * программы, не задана.
 */
public class EnvVariableNotDefinedException extends Exception {
    /**
     * Создаёт исключение, которое
     * выбрасывается, если переменная
     * окружения, используемая для работы
     * программы, не задана.
     * @param varName Имя переменной окружения
     */
    public EnvVariableNotDefinedException(String varName) {
        super("Environment variable \"" + varName + "\" is not defined");
    }
}
