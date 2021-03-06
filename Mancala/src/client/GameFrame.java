package client;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import javax.swing.*;

import server.*;

public class GameFrame extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    Game game;

    // atributos de controle de turno:
    private int currentTurn;
    private boolean player_type;

    // atributos que constituem o tabuleiro:
    private int[] player_board, enemy_board;
    private JLabel planoDeFundo;
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
    private JLabel jl_currentTurn;

    // Botões para acões de desistir e resetar o game
    private JButton desistir;
    private JButton restart;

    public GameFrame(Game game, boolean player_type) {
        super("Mancala");
        this.player_type = player_type;
        this.game = game;

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
        planoDeFundo.setIcon(new ImageIcon(getClass().getResource("/Mancala.png")));

        resetBoard(false);
    }

    private void insertComponents() {
        this.getContentPane().add(planoDeFundo);

    }

    private void resetBoard(boolean doForEnemy) {
        for (int i = 1; i < player_board.length; i++) {
            player_board[i] = 4;
            enemy_board[i] = 4;
        }
        player_board[0] = 0;
        enemy_board[0] = 0;
        this.currentTurn = 1;
        this.jl_currentTurn.setText("Turno do Player 1");        

        if (doForEnemy) {
            refreshTurns(this.currentTurn, true);
            refreshButtons(true);
        } else {
            refreshTurns(this.currentTurn, false);
            refreshButtons(false);
        }
    }

    public void refreshButtons(boolean doForEnemy) {
        //Atualiza os valores dos botões e desabilita os que forem zero: 

        buttonP1.setText(player_board[1] + "");
        if (player_board[1] == 0) {
            buttonP1.setEnabled(false);
        } else{
            buttonP1.setEnabled(true);
        }
        buttonP2.setText(player_board[2] + "");
        if (player_board[2] == 0) {
            buttonP2.setEnabled(false);
        } else{
            buttonP2.setEnabled(true);
        }
        buttonP3.setText(player_board[3] + "");
        if (player_board[3] == 0) {
            buttonP3.setEnabled(false);
        } else{
            buttonP3.setEnabled(true);
        }
        buttonP4.setText(player_board[4] + "");
        if (player_board[4] == 0) {
            buttonP4.setEnabled(false);
        } else{
            buttonP4.setEnabled(true);
        }
        buttonP5.setText(player_board[5] + "");
        if (player_board[5] == 0) {
            buttonP5.setEnabled(false);
        } else{
            buttonP5.setEnabled(true);
        }
        buttonP6.setText(player_board[6] + "");
        if (player_board[6] == 0) {
            buttonP6.setEnabled(false);
        } else{
            buttonP6.setEnabled(true);
        }
        buttonE6.setText(enemy_board[6] + "");
        if (enemy_board[6] == 0) {
            buttonE6.setEnabled(false);
        } else{
            buttonE6.setEnabled(true);
        }
        buttonE1.setText(enemy_board[5] + "");
        if (enemy_board[5] == 0) {
            buttonE1.setEnabled(false);
        } else{
            buttonE1.setEnabled(true);
        }
        
        buttonE2.setText(enemy_board[4] + "");
        if (enemy_board[4] == 0) {
            buttonE2.setEnabled(false);
        } else{
            buttonE2.setEnabled(true);
        }
        buttonE3.setText(enemy_board[3] + "");
        if (enemy_board[3] == 0) {
            buttonE3.setEnabled(false);
        } else{
            buttonE3.setEnabled(true);
        }
        buttonE4.setText(enemy_board[2] + "");
        if (enemy_board[2] == 0) {
            buttonE4.setEnabled(false);
        } else{
            buttonE4.setEnabled(true);
        }
        buttonE5.setText(enemy_board[1] + "");
        if (enemy_board[1] == 0) {
            buttonE5.setEnabled(false);
        } else{
            buttonE5.setEnabled(true);
        }

        player_score.setText(player_board[0] + "");
        enemy_score.setText(enemy_board[0] + "");

        if (doForEnemy) {
            try {
                this.game.enemy.updateBoard(this.player_board, this.enemy_board);
            } catch (RemoteException e) {
                JOptionPane.showMessageDialog(null, "" + e.getMessage());
                e.printStackTrace();
            }
        }
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

        restart.addActionListener(event -> resetBoard(true));
        desistir.addActionListener(event -> onGiveUp(this.player_type, true));

        this.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowClosing(WindowEvent e) {
                onClose();
                dispose();
                System.exit(game.getPort());
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
                    if (index_aux < -6) {
                        index_aux += 13;
                    }
                    // verifica captura.
                    if (i == board_aux_1[index] && board_aux_1[index_aux] == 0 && index_aux != 0
                            && board_aux_2[7 - index_aux] != 0) {
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
            // verifica se o jogador terá outro turno:
            if (!(index == board_aux_1[index])) {
                if (this.currentTurn == 1) {
                    this.currentTurn = 0;
                    this.jl_currentTurn.setText("Turno do Player 2");
                } else if (this.currentTurn == 0) {
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

            refreshButtons(true);
            refreshTurns(this.currentTurn, true);
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

    public void onGiveUp(boolean player_type, boolean doForEnemy) {
        if (player_type) {
            this.jl_currentTurn.setText("O Player 1 desistiu...");
        }

        if (!player_type) {
            this.jl_currentTurn.setText("O Player 2 desistiu...");
        }

        this.currentTurn = -1;

        refreshTurns(this.currentTurn, true);

        if (doForEnemy) {
            try {
                this.game.enemy.onSurrender(player_type);
            } catch (RemoteException e) {
                JOptionPane.showMessageDialog(null, "" + e.getMessage());
                e.printStackTrace();
            }
        }

    }

    private void refreshTurns(int currentTurn, boolean doForEnemy) {
        if (doForEnemy) {
            try {
                this.game.enemy.updateTurns(currentTurn);
            } catch (RemoteException e) {
                JOptionPane.showMessageDialog(null, "" + e.getMessage());
                e.printStackTrace();
            }
        }

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

    public void setJl_currentTurn(int currentTurn) {
        if (currentTurn == 1) {
            this.jl_currentTurn.setText("Vez do player 1");
        } else if (currentTurn == 0) {
            this.jl_currentTurn.setText("Vez do player 2");
        } else {
            this.jl_currentTurn.setText("Fim do jogo");
            if (verifyGameOver()) {
                String message = "";
                if (player_board[0] > enemy_board[0]) {
                    message = "Player 1 é o vencedor";
                } else if (player_board[0] < enemy_board[0]) {
                    message = "Player 2 é o vencedor";
                } else {
                    message = "Empate!";
                }
                this.jl_currentTurn.setText(message + "");
            }            
        }

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

    private void onClose(){
        try {
            this.game.enemy.onGameClose();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void start() {
        this.pack();
        this.setVisible(true);
    }
}
