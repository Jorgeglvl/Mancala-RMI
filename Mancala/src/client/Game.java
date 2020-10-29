package client;

import java.awt.*;
import java.awt.event.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;

import common.Utils;

public class Game extends JFrame {

    private int[] player_board, enemy_board;
    private int currentTurn;

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

    public Game(Home home, Socket connection, String connection_info, String title, boolean player_type){
        super("Mancala " + title);
        this.title = title;
        this.connection_info = connection_info;
        this.home = home;
        this.connection = connection;
        this.player_type = player_type;
        initComponents();
        configComponents();
        insertComponents();
        insertActions();
        start();
    }

    private void initComponents() {
        player_board = new int[7];
        enemy_board = new int[7];
        planoDeFundo = new JLabel();
        initButtons();
        initLabels();
    }

    private void configComponents() {
        this.setMinimumSize(new Dimension(1025, 680));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        planoDeFundo.setBounds(0, 0, 1024, 648);
        planoDeFundo.setIcon(new ImageIcon("Mancala/res/Mancala.png"));

        resetBoard();
    }

    private void insertComponents() {
        this.getContentPane().add(planoDeFundo);

    }

    private void insertActions() {

        if (this.player_type) {
            buttonP1.addActionListener(event -> makeMove(1, this.player_type));
            buttonP2.addActionListener(event -> makeMove(2, this.player_type));
            buttonP3.addActionListener(event -> makeMove(3, this.player_type));
            buttonP4.addActionListener(event -> makeMove(4, this.player_type));
            buttonP5.addActionListener(event -> makeMove(5, this.player_type));
            buttonP6.addActionListener(event -> makeMove(6, this.player_type));
        }

        if (!this.player_type) {
            buttonE6.addActionListener(event -> makeMove(6, this.player_type));
            buttonE1.addActionListener(event -> makeMove(5, this.player_type));
            buttonE2.addActionListener(event -> makeMove(4, this.player_type));
            buttonE3.addActionListener(event -> makeMove(3, this.player_type));
            buttonE4.addActionListener(event -> makeMove(2, this.player_type));
            buttonE5.addActionListener(event -> makeMove(1, this.player_type));
        }

        restart.addActionListener(event -> resetBoard());
        desistir.addActionListener(event -> onGiveUp(this.player_type));

        this.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowClosing(WindowEvent e) {
                Utils.sendMessage(connection, "GAME_CLOSE");
                home.getOpened_games().remove(connection_info);
                home.getConnected_listeners().get(connection_info).setOpened(false);
                home.getConnected_listeners().get(connection_info).setRunning(false);
                home.getConnected_listeners().remove(connection_info);
            }

            @Override
            public void windowClosed(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowIconified(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowActivated(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                // TODO Auto-generated method stub

            }
            
        });

    }

    private void resetBoard() {
        for (int i = 1; i < player_board.length; i++) {
            player_board[i] = 4;
            enemy_board[i] = 4;
        }
        player_board[0] = 0;
        enemy_board[0] = 0;
        this.currentTurn = 1;
        this.jl_currentTurn.setText("Turno do Player 1");

        refreshButtons(false);
        refreshTurns();
        refreshLabels();
    }

    private void makeMove(int index, Boolean player_type) {

        if ((this.currentTurn == 1 && player_type) || (this.currentTurn == 0 && !player_type)) {
            int index_aux;
            int[] board_aux_1 = new int[7];
            int[] board_aux_2 = new int[7];

            if (player_type) {
                board_aux_1 = player_board;
                board_aux_2 = enemy_board;
            } else if (!player_type) {
                board_aux_1 = enemy_board;
                board_aux_2 = player_board;
            }

            for (int i = 1; i <= board_aux_1[index]; i++) {
                index_aux = index - i;
                if (index_aux > -7 && index_aux < 0) {
                    board_aux_2[index_aux + 7]++;
                } else {
                    if(index_aux < -6){
                        index_aux += 13;
                    }
                    // verifica captura.
                    if (i == board_aux_1[index] && board_aux_1[index_aux] == 0 && index_aux != 0 && board_aux_2[7 - index_aux] != 0) {
                        int soma = 1;

                        soma += board_aux_2[7 - index_aux];
                        board_aux_1[0] += soma;
                        board_aux_2[7 - index_aux] = 0;
                        board_aux_1[index_aux] = 0;

                    } else {
                        board_aux_1[index_aux]++;
                    }
                }

            }
            //verifica se o jogador terá outro turno:
            if(!(index == board_aux_1[index])){
                if(this.currentTurn == 1){
                    this.currentTurn = 0;
                    this.jl_currentTurn.setText("Turno do Player 2");
                } 
                else if(this.currentTurn == 0){
                    this.currentTurn = 1;
                    this.jl_currentTurn.setText("Turno do Player 1");
                }
            }
            board_aux_1[index] = 0;

            if (player_type) {
                player_board = board_aux_1;
                enemy_board = board_aux_2;
            } else if (!player_type) {
                enemy_board = board_aux_1;
                player_board = board_aux_2;
            }

            if (verifyGameOver()) {
                for (int i = 1; i < 7; i++) {
                    player_board[0] += player_board[i];
                    player_board[i] = 0;
                    enemy_board[0] += enemy_board[i];
                    enemy_board[i] = 0;
                }
                String message = "";
                if (player_board[0] > enemy_board[0]) {
                    message = "Player 1 é o vencedor";
                } else if (player_board[0] < enemy_board[0]) {
                    message = "Player 2 é o vencedor";
                } else {
                    message = "Empate!";
                }
                this.currentTurn = -1;

                this.jl_currentTurn.setText(message + "");
            }

            refreshButtons(false);
            refreshLabels();
            refreshTurns();
        }

    }

    private boolean verifyGameOver() {

        if ((player_board[1] == 0) && (player_board[2] == 0) && (player_board[3] == 0) && (player_board[4] == 0)
                && (player_board[5] == 0) && (player_board[6] == 0)) {
            return true;
        }

        if ((enemy_board[1] == 0) && (enemy_board[2] == 0) && (enemy_board[3] == 0) && (enemy_board[4] == 0)
                && (enemy_board[5] == 0) && (enemy_board[6] == 0)) {
            return true;
        }

        return false;
    }

    private void onGiveUp(boolean player_type) {
        if (player_type) {
            this.jl_currentTurn.setText("O Player 1 desistiu...");
        }

        if (!player_type) {
            this.jl_currentTurn.setText("O Player 2 desistiu...");
        }

        this.currentTurn = -1;

        refreshTurns();
        refreshLabels();

    }

    private void start() {
        this.pack();
        this.setVisible(true);
    }

    private void initButtons() {
        this.getContentPane().setLayout(null);

        buttonP1 = new JButton(player_board[1] + "");
        buttonP1.setForeground(Color.CYAN);
        buttonP1.setFont(new Font("Dialog", Font.BOLD, 30));
        buttonP1.setBounds(182, 187, 76, 72);
        buttonP1.setOpaque(false);
        buttonP1.setContentAreaFilled(false);
        buttonP1.setBorderPainted(false);
        this.getContentPane().add(buttonP1);

        buttonP2 = new JButton(player_board[2] + "");
        buttonP2.setForeground(Color.CYAN);
        buttonP2.setFont(new Font("Dialog", Font.BOLD, 30));
        buttonP2.setBounds(299, 187, 76, 72);
        buttonP2.setOpaque(false);
        buttonP2.setContentAreaFilled(false);
        buttonP2.setBorderPainted(false);
        this.getContentPane().add(buttonP2);

        buttonP3 = new JButton(player_board[3] + "");
        buttonP3.setForeground(Color.CYAN);
        buttonP3.setFont(new Font("Dialog", Font.BOLD, 30));
        buttonP3.setBounds(419, 187, 76, 72);
        buttonP3.setOpaque(false);
        buttonP3.setContentAreaFilled(false);
        buttonP3.setBorderPainted(false);
        this.getContentPane().add(buttonP3);

        buttonP4 = new JButton(player_board[4] + "");
        buttonP4.setForeground(Color.CYAN);
        buttonP4.setFont(new Font("Dialog", Font.BOLD, 30));
        buttonP4.setBounds(541, 187, 76, 72);
        buttonP4.setOpaque(false);
        buttonP4.setContentAreaFilled(false);
        buttonP4.setBorderPainted(false);
        this.getContentPane().add(buttonP4);

        buttonP5 = new JButton(player_board[5] + "");
        buttonP5.setForeground(Color.CYAN);
        buttonP5.setFont(new Font("Dialog", Font.BOLD, 30));
        buttonP5.setBounds(661, 187, 76, 72);
        buttonP5.setOpaque(false);
        buttonP5.setContentAreaFilled(false);
        buttonP5.setBorderPainted(false);
        this.getContentPane().add(buttonP5);

        buttonP6 = new JButton(player_board[6] + "");
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

    private void initLabels() {

        player_score = new JLabel(this.player_board[0] + "");
        player_score.setForeground(Color.CYAN);
        player_score.setFont(new Font("Dialog", Font.BOLD, 30));
        player_score.setHorizontalAlignment(SwingConstants.CENTER);
        player_score.setBounds(65, 268, 57, 65);
        this.getContentPane().add(player_score);

        enemy_score = new JLabel(this.enemy_board[0] + "");
        enemy_score.setForeground(Color.ORANGE);
        enemy_score.setBounds(905, 320, 57, 65);
        enemy_score.setFont(new Font("Dialog", Font.BOLD, 30));
        enemy_score.setHorizontalAlignment(SwingConstants.CENTER);
        this.getContentPane().add(enemy_score);

        jl_currentTurn = new JLabel("Turno do Player 1");
        jl_currentTurn.setForeground(Color.GREEN);
        jl_currentTurn.setHorizontalAlignment(SwingConstants.CENTER);
        jl_currentTurn.setFont(new Font("Dialog", Font.BOLD, 30));
        jl_currentTurn.setBounds(365, 301, 332, 36);
        this.getContentPane().add(jl_currentTurn);

    }

    public void refreshButtons(boolean fromMessage) {

        buttonP1.setText(player_board[1] + "");
        buttonP2.setText(player_board[2] + "");
        buttonP3.setText(player_board[3] + "");
        buttonP4.setText(player_board[4] + "");
        buttonP5.setText(player_board[5] + "");
        buttonP6.setText(player_board[6] + "");
        buttonE6.setText(enemy_board[6] + "");
        buttonE1.setText(enemy_board[5] + "");
        buttonE2.setText(enemy_board[4] + "");
        buttonE3.setText(enemy_board[3] + "");
        buttonE4.setText(enemy_board[2] + "");
        buttonE5.setText(enemy_board[1] + "");

        player_score.setText(player_board[0] + "");
        enemy_score.setText(enemy_board[0] + "");

        if(!fromMessage){
            String p_board = Utils.boardToString(this.enemy_board);
            Utils.sendMessage(connection, "GAME_COMMAND_ATT_ENEMY;"  + p_board);

            String e_board = Utils.boardToString(this.player_board);
            Utils.sendMessage(connection, "GAME_COMMAND_ATT_PLAYER;"  + e_board);
        }

    }

    private void refreshLabels(){
        Utils.sendMessage(connection, "GAME_COMMAND_ATT_LABELS;"  + this.jl_currentTurn.getText());

    }

    private void refreshTurns(){
        String turno = Integer.toString(this.currentTurn);
        Utils.sendMessage(connection, "GAME_COMMAND_ATT_TURN;"  + turno);
    }

    public void setPlayer_board(int[] player_board) {
        this.player_board = player_board;
    }

    public void setEnemy_board(int[] enemy_board) {
        this.enemy_board = enemy_board;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

	public JLabel getJl_currentTurn() {
		return jl_currentTurn;
	}

	public void setJl_currentTurn(JLabel jl_currentTurn) {
		this.jl_currentTurn = jl_currentTurn;
	}

}
