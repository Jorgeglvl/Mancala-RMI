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


public class GameRMI extends UnicastRemoteObject implements Communication {

    private String host;
	private int porta;
	private String mensagemEnv = "";
	private Communication oponente;
	private Registry registro;
	private boolean esperando = true;
	private Game win;
	//--------------------------------------------//--------------------------------------------//
	private ArrayList<Integer> historico;
	private boolean fim = false;
	private int novamente = 0;
	private int jogador = 1;
	private int jogando = 0;
	private static final int jogador1 = 0;
	private static final int jogador2 = 1;

	public GameRMI(int[] player_board, int[] enemy_board) throws RemoteException{
		super();
	}
    
}
