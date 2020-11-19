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

	protected Game game;
	protected Color clientColour;

	public Player(String ip, int port) throws RemoteException {
		this.ip = ip;
		this.port = port;

		new Game(this);
    }

	public String getIp() {
		return ip;
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
