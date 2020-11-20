package client;

import java.awt.EventQueue;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import common.Communication;


public class Player extends UnicastRemoteObject implements Communication {

    private String ip;
	private int port;
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
		
	}

	@Override
	public void desistir(int jogador, boolean doForEnemy) throws RemoteException {
		// TODO Auto-generated method stub

		if (doForEnemy) {
			enemy.desistir(jogador, false);
		}

	}

	@Override
	public void finalizaJogo(boolean doForEnemy) throws RemoteException {
		// TODO Auto-generated method stub

		if (doForEnemy) {
			enemy.finalizaJogo(false);
		}

	}

	@Override
	public void notificaSaida(int jogador, boolean doForEnemy) throws RemoteException {
		// TODO Auto-generated method stub

		if (doForEnemy) {
			enemy.notificaSaida(jogador, false);
		}

	}

	@Override
	public void connect() throws MalformedURLException, RemoteException, NotBoundException {
		
		enemy = (Communication)registry.lookup("//"+ip+":"+port+"/Cliente");
		System.out.println("Jogador 2 conectado");
	}

	@Override
	public void stopWaiting() throws RemoteException {
		waiting = false;
	}
    
}
