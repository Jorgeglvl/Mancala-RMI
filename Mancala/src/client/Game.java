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
    
    private JButton buttonP1;
    private JButton buttonP2;
	private JButton buttonP3;
	private JButton buttonP4;
	private JButton buttonP5;
	private JButton buttonP6;	
	private JButton buttonE1;
	private JButton buttonE2;
	private JButton buttonE3;
	private JButton buttonE4;
    private JButton buttonE5;
    private JButton buttonE6;
    private JLabel player_score;
    private JLabel enemy_score;
    
    private JButton desistir;
    private JButton restart;
    private JLabel planoDeFundo;
    private JLabel jl_currentTurn;

    private Home home;
    private String title;
    private Socket connection;
    private String connection_info;

    public boolean player_type;

    private final static int PLAYER_TURN = 1;
    private final static int ENEMY_TURN = 0;


//    public Game(Home home, Socket connection, String connection_info, String title){
    public Game(boolean player_type){
        // super("Mancala " + title);
        // this.title = title;
        // this.connection_info = connection_info;
        // this.home = home;
        // this.connection = connection;
        this.player_type = player_type;
        initComponents();
        configComponents();
        insertComponents();
        insertActions();
        start();
    }

    private void initComponents(){
        player_board = new int[7];
        enemy_board = new int[7];
        planoDeFundo = new JLabel();
        initButtons();
        initLabels();

    }

    private void configComponents(){
        this.setMinimumSize(new Dimension(1025, 680));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        planoDeFundo.setBounds(0, 0, 1024, 648);
		planoDeFundo.setIcon(new ImageIcon("Mancala/res/Mancala.png"));

        resetBoard();
    }

    private void insertComponents(){
        this.getContentPane().add(planoDeFundo);

    }

    private void insertActions(){

        if(player_type){
            buttonP1.addActionListener(event -> makeMove(1, PLAYER_TURN));
            buttonP2.addActionListener(event -> makeMove(2, PLAYER_TURN));
            buttonP3.addActionListener(event -> makeMove(3, PLAYER_TURN));
            buttonP4.addActionListener(event -> makeMove(4, PLAYER_TURN));
            buttonP5.addActionListener(event -> makeMove(5, PLAYER_TURN));
            buttonP6.addActionListener(event -> makeMove(6, PLAYER_TURN));
        }
        
        if(!player_type){
            buttonE6.addActionListener(event -> makeMove(6, ENEMY_TURN));
            buttonE1.addActionListener(event -> makeMove(5, ENEMY_TURN));
            buttonE2.addActionListener(event -> makeMove(4, ENEMY_TURN));
            buttonE3.addActionListener(event -> makeMove(3, ENEMY_TURN));
            buttonE4.addActionListener(event -> makeMove(2, ENEMY_TURN));
            buttonE5.addActionListener(event -> makeMove(1, ENEMY_TURN));
        }        

        restart.addActionListener(event -> resetBoard());

    }

    private void resetBoard(){
        for (int i = 1; i < player_board.length; i++) {
            player_board[i] = 4;
            enemy_board[i] = 4;
        }
        player_board[0] = 0;
        enemy_board[0] = 0;

        refreshButtons();
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
                if(i == board_aux_1[index] && board_aux_1[index_aux] == 0 && index_aux != 0){
                    int soma = 1;

                    soma += board_aux_2[7-index_aux];            
                    board_aux_1[0] += soma;
                    board_aux_2[7-index_aux] = 0;
                    board_aux_1[index_aux] = 0;

                } else {board_aux_1[index_aux]++;}
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
        
        refreshButtons();
        if(verifyGameOver()){
            String message = "";
            if(player_board[0] > enemy_board[0]){
                message = "Player 1 é o vencedor";
            }else if(player_board[0] < enemy_board[0]){
                message = "Player 2 é o vencedor";
            }else{
                message = "Empate!";
            }           
            
            this.jl_currentTurn.setText(message+"");
        }        
        
    }

    private boolean verifyGameOver(){

        if((player_board[1] == 0) && (player_board[2] == 0) && (player_board[3] == 0) && (player_board[4] == 0) && (player_board[5] == 0) && (player_board[6] == 0)){
            return true;
        }

        if((enemy_board[1] == 0) && (enemy_board[2] == 0) && (enemy_board[3] == 0) && (enemy_board[4] == 0) && (enemy_board[5] == 0) && (enemy_board[6] == 0)){
            return true;
        }

        return false;
    }

    private void start(){
        this.pack();
        this.setVisible(true);
    }


    private void initButtons(){
        this.getContentPane().setLayout(null);
		
		buttonP1 = new JButton(player_board[1]+"");
		buttonP1.setForeground(Color.CYAN);
		buttonP1.setFont(new Font("Dialog", Font.BOLD, 30));
		buttonP1.setBounds(182, 187, 76, 72);
		buttonP1.setOpaque(false);
		buttonP1.setContentAreaFilled(false);
		buttonP1.setBorderPainted(false);
        this.getContentPane().add(buttonP1);
        
        buttonP2 = new JButton(player_board[2]+"");
		buttonP2.setForeground(Color.CYAN);
		buttonP2.setFont(new Font("Dialog", Font.BOLD, 30));
		buttonP2.setBounds(299, 187, 76, 72);
		buttonP2.setOpaque(false);
		buttonP2.setContentAreaFilled(false);
		buttonP2.setBorderPainted(false);
		this.getContentPane().add(buttonP2);
		
		buttonP3 = new JButton(player_board[3]+"");
		buttonP3.setForeground(Color.CYAN);
		buttonP3.setFont(new Font("Dialog", Font.BOLD, 30));
		buttonP3.setBounds(419, 187, 76, 72);
		buttonP3.setOpaque(false);
		buttonP3.setContentAreaFilled(false);
		buttonP3.setBorderPainted(false);
		this.getContentPane().add(buttonP3);
		
		buttonP4 = new JButton(player_board[4]+"");
		buttonP4.setForeground(Color.CYAN);
		buttonP4.setFont(new Font("Dialog", Font.BOLD, 30));
		buttonP4.setBounds(541, 187, 76, 72);
		buttonP4.setOpaque(false);
		buttonP4.setContentAreaFilled(false);
		buttonP4.setBorderPainted(false);
		this.getContentPane().add(buttonP4);
		
		buttonP5 = new JButton(player_board[5]+"");
		buttonP5.setForeground(Color.CYAN);
		buttonP5.setFont(new Font("Dialog", Font.BOLD, 30));
		buttonP5.setBounds(661, 187, 76, 72);
		buttonP5.setOpaque(false);
		buttonP5.setContentAreaFilled(false);
		buttonP5.setBorderPainted(false);
		this.getContentPane().add(buttonP5);
		
		buttonP6 = new JButton(player_board[6]+"");
		buttonP6.setForeground(Color.CYAN);
		buttonP6.setFont(new Font("Dialog", Font.BOLD, 30));
		buttonP6.setBounds(780, 187, 76, 72);
		buttonP6.setOpaque(false);
		buttonP6.setContentAreaFilled(false);
		buttonP6.setBorderPainted(false);
		this.getContentPane().add(buttonP6);
		
		buttonE6 = new JButton(Integer.toString(enemy_board[6]));
		buttonE6.setForeground(Color.ORANGE);
		buttonE6.setFont(new Font("Dialog", Font.BOLD, 30));
		buttonE6.setBounds(182, 393, 76, 72);
		buttonE6.setOpaque(false);
		buttonE6.setContentAreaFilled(false);
		buttonE6.setBorderPainted(false);
		this.getContentPane().add(buttonE6);
		
		buttonE1 = new JButton(Integer.toString(enemy_board[5]));
		buttonE1.setForeground(Color.ORANGE);
		buttonE1.setFont(new Font("Dialog", Font.BOLD, 30));
		buttonE1.setBounds(299, 393, 76, 72);
		buttonE1.setOpaque(false);
		buttonE1.setContentAreaFilled(false);
		buttonE1.setBorderPainted(false);
		this.getContentPane().add(buttonE1);
		
		buttonE2 = new JButton(Integer.toString(enemy_board[4]));
		buttonE2.setForeground(Color.ORANGE);
		buttonE2.setFont(new Font("Dialog", Font.BOLD, 30));
		buttonE2.setBounds(419, 393, 76, 72);
		buttonE2.setOpaque(false);
		buttonE2.setContentAreaFilled(false);
		buttonE2.setBorderPainted(false);
		this.getContentPane().add(buttonE2);
		
		buttonE3 = new JButton(Integer.toString(enemy_board[3]));
		buttonE3.setForeground(Color.ORANGE);
		buttonE3.setFont(new Font("Dialog", Font.BOLD, 30));
		buttonE3.setBounds(541, 393, 76, 72);
		buttonE3.setOpaque(false);
		buttonE3.setContentAreaFilled(false);
		buttonE3.setBorderPainted(false);
		this.getContentPane().add(buttonE3);
		
		buttonE4 = new JButton(Integer.toString(enemy_board[2]));
		buttonE4.setForeground(Color.ORANGE);
		buttonE4.setFont(new Font("Dialog", Font.BOLD, 30));
		buttonE4.setBounds(661, 393, 76, 72);
		buttonE4.setOpaque(false);
		buttonE4.setContentAreaFilled(false);
		buttonE4.setBorderPainted(false);
		this.getContentPane().add(buttonE4);
		
		buttonE5 = new JButton(Integer.toString(enemy_board[1]));
		buttonE5.setForeground(Color.ORANGE);
		buttonE5.setFont(new Font("Dialog", Font.BOLD, 30));
		buttonE5.setBounds(780, 393, 76, 72);
		buttonE5.setOpaque(false);
		buttonE5.setContentAreaFilled(false);
		buttonE5.setBorderPainted(false);
		this.getContentPane().add(buttonE5);
		
		desistir = new JButton("Desistir");
		desistir.setBounds(10, 556, 101, 25);
        this.getContentPane().add(desistir);
        
        restart = new JButton("Restart");
		restart.setBounds(10, 593, 101, 25);
		this.getContentPane().add(restart);

    }

    private void initLabels(){
		
		player_score = new JLabel(this.player_board[0]+"");
		player_score.setForeground(Color.CYAN);
		player_score.setFont(new Font("Dialog", Font.BOLD, 30));
		player_score.setHorizontalAlignment(SwingConstants.CENTER);
		player_score.setBounds(65, 268, 57, 65);
		this.getContentPane().add(player_score);
		
		enemy_score = new JLabel(this.enemy_board[0]+"");
		enemy_score.setForeground(Color.ORANGE);
		enemy_score.setBounds(905, 320, 57, 65);
		enemy_score.setFont(new Font("Dialog", Font.BOLD, 30));
		enemy_score.setHorizontalAlignment(SwingConstants.CENTER);
		this.getContentPane().add(enemy_score);
		
        jl_currentTurn = new JLabel("Sua Vez");
        jl_currentTurn.setForeground(Color.GREEN);
		jl_currentTurn.setHorizontalAlignment(SwingConstants.CENTER);
		jl_currentTurn.setFont(new Font("Dialog", Font.BOLD, 30));
		jl_currentTurn.setBounds(365, 301, 332, 36);
		this.getContentPane().add(jl_currentTurn);
		
	}

    public void refreshButtons(){
		
		buttonP1.setText(player_board[1]+"");
		buttonP2.setText(player_board[2]+"");
		buttonP3.setText(player_board[3]+"");
		buttonP4.setText(player_board[4]+"");
		buttonP5.setText(player_board[5]+"");
		buttonP6.setText(player_board[6]+"");
		buttonE6.setText(enemy_board[6]+"");
		buttonE1.setText(enemy_board[5]+"");
		buttonE2.setText(enemy_board[4]+"");
		buttonE3.setText(enemy_board[3]+"");
		buttonE4.setText(enemy_board[2]+"");
        buttonE5.setText(enemy_board[1]+"");
        
		player_score.setText(player_board[0]+"");
		enemy_score.setText(enemy_board[0]+"");
		
	}

    public static void main(String[] args) {
        Game game = new Game(true);
    }

}
