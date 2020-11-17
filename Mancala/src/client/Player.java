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
	private Game win;
	//--------------------------------------------//--------------------------------------------//
	private boolean fim = false;
	private int novamente = 0;
	private int jogador = 1;
	private int jogando = 0;
	private static final boolean jogador1 = true;
	private static final boolean jogador2 = false;

	public Player(int[] player_board, int[] enemy_board, String ip, int port) throws RemoteException{
		super();

		createRunnable();

		this.ip = ip;
		this.port = port;

		try {
			//win.setMensagemEnviada("Conectando ao servidor");
			registry = LocateRegistry.getRegistry(port);
			registry.bind("//"+ip+":"+port+"/Cliente",this);
			//win.setMensagemEnviada("Conectado");
			//win.setMensagemEnviada("Cliente Registrado!");
			enemy = (Communication)registry.lookup("//"+ip+":"+port+"/Servidor");
			//win.setMensagemEnviada("Jogador "+(jogador1+1)+" conectado");
			enemy.connect();
			stopWaiting();
			enemy.stopWaiting();
		}
		catch (ConnectException|AlreadyBoundException e) {
			try {
				//win.setMensagemEnviada("Não há servidores disponivel");
				//win.setMensagemEnviada("Registrando servidor");
				registry = LocateRegistry.createRegistry(port);
				registry.bind("//"+ip+":"+port+"/Servidor",this);
				//win.setMensagemEnviada("Servidor Registrado!");
				//win.setMensagemEnviada("Aguardando jogador");
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

		//inicia o jogo
	}

	public void connect() throws MalformedURLException, RemoteException, NotBoundException {
		
		enemy = (Communication)registry.lookup("//"+ip+":"+port+"/Cliente");

		//win.setMensagemEnviada("Jogador "+(jogador2+1)+" conectado");
	}

	public void stopWaiting() throws RemoteException {
		waiting = false;
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
    
}
