package io.github.caesiumfox.lab07.server;

import java.util.*;

public class Shell extends Thread {
    private Scanner input;
    public Shell(Scanner input) {
        this.input = input;
    }

    @Override
    public void run() {
        while (Server.running) {
            String command = input.nextLine().trim();
            if (command.equals("exit")) {
                System.out.println("Finishing processes...");
                Server.stop();
            } else {
                System.out.println("Unknown command: " + command);
            }
        }
    }
}
