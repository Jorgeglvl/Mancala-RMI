package client;

import java.awt.CardLayout;
import java.awt.Color;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Player implements Serializable {

	private String ip;
	private int port;
<<<<<<< HEAD
	private String mensagemEnv = "";
	private Communication enemy;
	private Registry registry;
	private boolean waiting = true;
	private GameInterface game;

	private boolean fim = false;
	private int novamente = 0;
	private int jogador = 1;
	private int jogando = 0;
	private static final int jogador1 = 0;
	private static final int jogador2 = 1;
	private boolean player_type;

	public Player(int[] player_board, int[] enemy_board, String ip, int port) throws RemoteException{
		super();

		this.ip = ip;
		this.port = port;

		try {
			System.out.println("Conectando ao servidor");
			registry = LocateRegistry.getRegistry(port);
			registry.bind("//"+ip+":"+port+"/Cliente",this);
			System.out.println("Conectado");
			this.player_type = false;
			System.out.println("Entrando no jogo");
			enemy = (Communication)registry.lookup("//"+ip+":"+port+"/Servidor");
			System.out.println("Jogador 1 já conectado");
			System.out.println();
			enemy.connect();
			stopWaiting();
			enemy.stopWaiting();
		}
		catch (ConnectException|AlreadyBoundException e) {
			try {
				System.out.println("Não há servidores disponiveis");
				System.out.println("Registrando como servidor");
				this.player_type = true;
				registry = LocateRegistry.createRegistry(port);
				registry.bind("//"+ip+":"+port+"/Servidor",this);
				System.out.println("Aguardando outro jogador");
				this.jogador = 0;
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

		game = new GameInterface(this,this.player_type);
	}

	@Override
	public void setMensagemRec(String mensagemRec) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBoards(int[] player_board, int[] enemy_board, boolean doForEnemy) throws RemoteException {
		game.setPlayer_board(player_board);
		game.setEnemy_board(enemy_board);
		game.refreshButtons(true);

		if (doForEnemy) {
			enemy.setBoards(player_board, enemy_board, false);
		}

	}

	@Override
	public void setJogador(int currentTurn, boolean doForEnemy) throws RemoteException, NotBoundException {

		enemy = (Communication)registry.lookup("//"+ip+":"+port+"/setJogador");
		
		System.out.println("Chamou corretamente");
		
		// game.setCurrentTurn(currentTurn);
		// game.setJl_currentTurn(currentTurn);

		// if (doForEnemy) {
		// 	enemy.setJogador(jogador, false);
		// }		
		
=======

	protected Game game;
	protected Color clientColour;

	public Player(String ip, int port) throws RemoteException {
		this.ip = ip;
		this.port = port;

		new Game(this);
    }

	public String getIp() {
		return ip;
>>>>>>> 4c1b92b9263f2d0f1e499b8c4c39976470cca6ba
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
  
}
