package common;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Communication extends Remote{
    public void conecta() throws MalformedURLException, RemoteException, NotBoundException;
	public void setMensagemRec(String mensagemRec) throws RemoteException;
	public void setTabuleiro(int[] player_board, int[] enemy_board) throws RemoteException;
	public void setJogador(int jogador) throws RemoteException;
	public void desistir(int jogador) throws RemoteException;
	public void finalizaJogo() throws RemoteException;
	public void notificaSaida(int jogador) throws RemoteException;
	public void saiEspera() throws RemoteException;
}
