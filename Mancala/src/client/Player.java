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
	//--------------------------------------------//--------------------------------------------//
	private boolean fim = false;
	private int novamente = 0;
	private int jogador = 1;
	private int jogando = 0;
	private static final int jogador1 = 0;
	private static final int jogador2 = 1;
	private boolean player_type;

	public Player(int[] player_board, int[] enemy_board, String ip, int port) throws RemoteException{
		super();

		createRunnable();

		this.ip = ip;
		this.port = port;

		try {
			//win.setMensagemEnviada("Conectando ao servidor");
			System.out.println("Conectando ao servidor");
			registry = LocateRegistry.getRegistry(port);
			registry.bind("//"+ip+":"+port+"/Cliente",this);
			//win.setMensagemEnviada("Conectado");
			System.out.println("Conectado");
			this.player_type = false;
			//win.setMensagemEnviada("Cliente Registrado!");
			System.out.println("Cliente Registrado!");
			enemy = (Communication)registry.lookup("//"+ip+":"+port+"/Servidor");
			//win.setMensagemEnviada("Jogador "+(jogador1+1)+" conectado");
			System.out.println("Jogador 1 conectado");
			System.out.println();
			enemy.connect();
			stopWaiting();
			enemy.stopWaiting();
		}
		catch (ConnectException|AlreadyBoundException e) {
			try {
				//win.setMensagemEnviada("Não há servidores disponivel");
				System.out.println("Não há servidores disponivel");
				//win.setMensagemEnviada("Registrando servidor");
				System.out.println("Registrando servidor");
				this.player_type = true;
				registry = LocateRegistry.createRegistry(port);
				registry.bind("//"+ip+":"+port+"/Servidor",this);
				//win.setMensagemEnviada("Servidor Registrado!");
				System.out.println("Servidor Registrado!");
				//win.setMensagemEnviada("Aguardando jogador");
				System.out.println("Aguardando jogador");
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

	private void createRunnable() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//win.getJFrame().setVisible(true);
					//win.varreBotao();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
	public void setJogador(int currentTurn, boolean doForEnemy) throws RemoteException {
		game.setCurrentTurn(currentTurn);
		game.setJl_currentTurn(currentTurn);

		if (doForEnemy) {
			enemy.setJogador(jogador, false);
		}		
		
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

		//win.setMensagemEnviada("Jogador "+(jogador2+1)+" conectado");
		System.out.println("Jogador 2 conectado");
	}

	@Override
	public void stopWaiting() throws RemoteException {
		waiting = false;
	}
    
}
