package io.github.caesiumfox.lab06.server;

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

            }
            catch (Exception e) {

            }
        }
    }
}
