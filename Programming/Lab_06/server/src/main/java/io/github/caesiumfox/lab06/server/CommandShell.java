package io.github.caesiumfox.lab06.server;

import java.util.Scanner;

/**
 * Класс отвечающий за обработку команд
 * в текстовом виде.
 * Затем утанавливаются флаги
 * {@link CommandManager},
 * который их выполняет.
 * Работает только в текстовом режиме
 * параллельно с обработчиком команд
 * клиента.
 */
public class CommandShell extends Thread {
    private CommandManager correspondingManager;
    private final Scanner input;
    private boolean running;

    public CommandShell(Scanner input) {
        this.input = input;
    }

    @Override
    public void run() {
        try {
            running = true;
            while (running) {
                String comm = input.nextLine().trim();
                switch (comm) {
                    case "save":
                        CommandManager.itsTimeToSave = true;
                        break;
                    case "exit":
                        CommandManager.itsTimeToStop = true;
                        running = false;
                        break;
                    default:
                        System.out.println("Unknown command: " + comm);
                }
            }
        } catch (Exception e) {
            if(correspondingManager != null)
                correspondingManager.urgentStop();
            System.out.println("Something wrong happened in CommandShell:");
            System.out.println(e.getMessage());
            Server.logger.severe("Error: " + e.getMessage());
        }
    }

    public void urgentStop() {
        running = false;
    }

    public void setCorrespondingManager(CommandManager correspondingManager) {
        this.correspondingManager = correspondingManager;
    }
}
