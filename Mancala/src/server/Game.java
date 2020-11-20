package server;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import javax.swing.JOptionPane;

import common.*;
import client.*;


public class Game extends UnicastRemoteObject implements GameInterface {


    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public Registry registry;
    public GameInterface enemy;

	private GameFrame gameFrame;
	private ChatFrame chatFrame;
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
				registry = LocateRegistry.createRegistry(port);
				registry.bind("//"+ip+":"+port+"/Server",this);				
				System.out.println("Servidor Registrado!");				
				System.out.println("Aguardando jogador");
				this.player_type = true;
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, "" + e.getMessage());
				e2.printStackTrace();
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "" + e.getMessage());
			e.printStackTrace();
		}

		while(waiting) {
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null, "" + e.getMessage());
				e.printStackTrace();
			}
        }
        
		gameFrame = new GameFrame(this, this.player_type);
		chatFrame = new ChatFrame(this, this.player_type);

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
    public void updateChat(String text) throws RemoteException {
		this.chatFrame.append_message(text);
    }

    @Override
    public void updateBoard(int[] player_board, int[] enemy_board) throws RemoteException {
		this.gameFrame.setPlayer_board(player_board);
		this.gameFrame.setEnemy_board(enemy_board);
        this.gameFrame.refreshButtons(false);
    }

    @Override
    public void onSurrender(boolean player_type) throws RemoteException {
        this.gameFrame.onGiveUp(player_type, false);

    }

	@Override
	public void updateTurns(int currentTurn) throws RemoteException {
		this.gameFrame.setJl_currentTurn(currentTurn);
		this.gameFrame.setCurrentTurn(currentTurn);
	}

	@Override
	public void onGameClose() throws RemoteException {
		this.chatFrame.append_message("<b>Seu oponente deixou o jogo... </b><br>");
		this.gameFrame.dispose();
	}

}
