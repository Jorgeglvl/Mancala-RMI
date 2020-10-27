package client;

import java.awt.*;
import java.awt.event.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;

import common.Utils;

public class Game extends JFrame{

    private int[] player_board, enemy_board;
    

    private JLabel jl_title;

    private Home home;
    private String title;
    private Socket connection;
    private String connection_info;

    private final static int PLAYER_TURN = 1;
    private final static int ENEMY_TURN = 0;


//    public Game(Home home, Socket connection, String connection_info, String title){
    public Game(){
        // super("Mancala " + title);
        // this.title = title;
        // this.connection_info = connection_info;
        // this.home = home;
        // this.connection = connection;
        initComponents();
        configComponents();
        insertComponents();
        insertActions();
        start();
    }

    private void initComponents(){
        player_board = new int[7];
        enemy_board = new int[7];
        //jl_title = new JLabel(connection_info.split(":")[0], SwingConstants.CENTER);

    }

    private void configComponents(){
        this.setMinimumSize(new Dimension(720, 480));
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        resetBoard();
        makeMove(1, PLAYER_TURN);
        makeMove(5, PLAYER_TURN);
    }

    private void insertComponents(){

    }

    private void insertActions(){

    }

    private void resetBoard(){
        for (int i = 1; i < player_board.length; i++) {
            player_board[i] = 4;
            enemy_board[i] = 4;
        }
        player_board[0] = 0;
        enemy_board[0] = 0;

        //enviar mensagem para resetar tabuleiro via socket??
    }

    private void makeMove(int index, int currentPlayer){

        int index_aux;
        int[] board_aux_1 = new int[7];
        int[] board_aux_2 = new int[7];

        if(currentPlayer == PLAYER_TURN){
            board_aux_1 = player_board;
            board_aux_2 = enemy_board;
        } else if(currentPlayer == ENEMY_TURN){
            board_aux_1 = enemy_board;
            board_aux_2 = player_board;
        }

        for(int i = 1; i <= board_aux_1[index]; i++){
            index_aux = index-i;             
            if(index_aux < 0){
                board_aux_2[index_aux + 7 ]++;
            }else{
                //verifica captura.
                if(i == board_aux_1[index] && board_aux_1[index_aux] == 0){
                    System.out.println("_CAPTURA_");
                }
                board_aux_1[index_aux]++;                
            }

        }
        board_aux_1[index] = 0;

        if(currentPlayer == PLAYER_TURN){
           player_board = board_aux_1;
           enemy_board = board_aux_2;
        } else if(currentPlayer == ENEMY_TURN){
            enemy_board = board_aux_1;
            player_board = board_aux_2;
        }

        verifyGameOver();
        printBoard();
        
    }

    private void printBoard(){
        for(int i = 0; i < player_board.length; i++){
            System.out.printf("[" + player_board[i] + "]  ");
        }
        
        System.out.println();

        for(int i = (enemy_board.length - 1); i >= 0; i--){
            System.out.printf("[" + enemy_board[i] + "]  ");
        }

        System.out.println("\n");
    }

    private boolean verifyGameOver(){
        if((player_board[1] == 0) && (player_board[2] == 0) && (player_board[3] == 0) && (player_board[4] == 0) && (player_board[5] == 0) && (player_board[6] == 0) && (player_board[7] == 0)){
            return true;
        }

        if((enemy_board[1] == 0) && (enemy_board[2] == 0) && (enemy_board[3] == 0) && (enemy_board[4] == 0) && (enemy_board[5] == 0) && (enemy_board[6] == 0) && (enemy_board[7] == 0)){
            return true;
        }

        return false;
    }

    private void start(){
        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        Game game = new Game();
    }

}
