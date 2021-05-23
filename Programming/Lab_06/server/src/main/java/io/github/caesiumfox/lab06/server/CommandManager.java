package io.github.caesiumfox.lab06.server;

import io.github.caesiumfox.lab06.common.KeyWord;

public class CommandManager {
    private DatabaseManager database;
    private boolean running;

    public CommandManager(DatabaseManager database) {
        Server.logger.info("Started command manager initialization");
        this.database = database;
        Server.logger.info("Initialized command manager");
    }

    public void run() {
        while(running) {
            try {
                //
            } catch (Exception e) {
                System.out.println("Failed to run command. Error:");
                System.out.println(e.getMessage());
            }
        }
    }

    private void handleCommands() throws Exception {
        NetworkManager.receive();
        KeyWord code = KeyWord.getKeyWord(NetworkManager.byteBuffer.get());

    }

}
