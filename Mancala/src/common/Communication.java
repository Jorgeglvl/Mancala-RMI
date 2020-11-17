package common;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Communication extends Remote{
    public void connect() throws MalformedURLException, RemoteException, NotBoundException;//OK
	public void setMensagemRec(String mensagemRec) throws RemoteException;
	public void setBoards(int[] player_board, int[] enemy_board, boolean doForEnemy) throws RemoteException;
	public void setJogador(int jogador, boolean doForEnemy) throws RemoteException;
	public void desistir(int jogador, boolean doForEnemy) throws RemoteException;
	public void finalizaJogo(boolean doForEnemy) throws RemoteException;
	public void notificaSaida(int jogador, boolean doForEnemy) throws RemoteException;
	public void stopWaiting() throws RemoteException;//OK
}
