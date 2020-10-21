package client;

import java.io.IOException;
import java.net.Socket;

import common.Utils;

public class ClientListener implements Runnable {

    private boolean running;
    private boolean isOpened;
    private Home home;
    private Socket connection;
    private String connection_info;
    private Game game;

    public ClientListener(Home home, Socket connection) {
        this.running = false;
        this.isOpened = false;
        this.home = home;
        this.connection = connection;
        this.connection_info = null;
        this.game = null;

    }

    @Override
    public void run() {
        running = true;
        String message;
        while (running) {
            message = Utils.receiveMessage(connection);
            if (message == null || message.equals("GAME_CLOSE")) {
                if (isOpened) {
                    home.getOpened_games().remove(connection_info);
                    home.getConnected_listeners().remove(connection_info);
                    isOpened = false;
                    try {
                        connection.close();
                    } catch (IOException e) {
                        System.err.println("[ClientListener:Run] -> " + e.getMessage());
                    }
                    game.dispose();
                }
                running = false;
            } else {
                String[] fields = message.split("|");
                if(fields.length > 1){
                    if(fields[0].equals("OPEN_GAME")){
                        String[] splited = fields[1].split(":");
                        connection_info = fields[1];
                        if(!isOpened){
                            home.getOpened_games().add(connection_info);
                            home.getConnected_listeners().put(connection_info, this);
                            isOpened = true;
                            game = new Game(home, connection, connection_info, home.getConnection_info());
                        }
                    }
                }
            }
            System.out.println("Mensagem: " + message);
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean isOpened) {
        this.isOpened = isOpened;
    }

    public Game getChat() {
        return game;
    }

    public void setChat(Game game) {
        this.game = game;
    }
    
}
