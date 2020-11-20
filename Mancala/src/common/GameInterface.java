package common;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameInterface extends Remote{
    public void connect() throws RemoteException, MalformedURLException, NotBoundException;
    public void stopWaiting() throws RemoteException;
    public void updateChat(String text) throws  RemoteException;
    public void updateBoard(int[] player_board, int[] enemy_board) throws  RemoteException;
    public void updateTurns(int currentTurn) throws RemoteException;
    public void onSurrender(boolean player_type) throws  RemoteException;
    public void onGameClose() throws RemoteException;
}
