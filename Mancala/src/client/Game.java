package client;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class Game extends UnicastRemoteObject implements GameInterface{


    public Registry registry;
    public GameInterface enemy;

    private GameFrame gameFrame;
    private int currentTurn;

    private Player player;

    public boolean player_type;
    private String ip;
    private int port;

    private boolean waiting = true;

    public Game(Player player) throws RemoteException {
        
        this.player = player;
        this.ip = this.player.getIp();
        this.port = this.player.getPort();

        try {			
			System.out.println("Conectando ao servidor");
			registry = LocateRegistry.getRegistry(port);
			registry.bind("//"+ip+":"+port+"/Client",this);			
			System.out.println("Conectado");
			this.player_type = false;			
			System.out.println("Cliente Registrado!");
            this.enemy = (GameInterface)registry.lookup("//"+ip+":"+port+"/Server");
            this.player_type = false;		
			System.out.println("Jogador 1 conectado");
			System.out.println();
			this.enemy.connect();
			stopWaiting();
			this.enemy.stopWaiting();
		}
		catch (ConnectException|AlreadyBoundException e) {
			try {
				System.out.println("Não há servidores disponíveis");				
				System.out.println("Registrando servidor");
				this.player_type = true;
				registry = LocateRegistry.createRegistry(port);
				registry.bind("//"+ip+":"+port+"/Server",this);				
				System.out.println("Servidor Registrado!");				
				System.out.println("Aguardando jogador");
				this.player_type = true;
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		while(waiting) {
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        
        gameFrame = new GameFrame(this, player_type);

    }
    
    @Override
	public void connect() throws MalformedURLException, RemoteException, NotBoundException {		
		this.enemy = (GameInterface)registry.lookup("//"+ip+":"+port+"/Client");
		System.out.println("Jogador 2 conectado");
    }
    
    @Override
	public void stopWaiting() throws RemoteException {
		waiting = false;
	}

    @Override
    public void updateChat(String playerName, String text) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateBoard(int[] player_board, int[] enemy_board) throws RemoteException {
        this.gameFrame.refreshButtons(false);
    }

    @Override
    public void handleSurrender() throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void handleRestart() throws RemoteException {
        // TODO Auto-generated method stub

    }

}
