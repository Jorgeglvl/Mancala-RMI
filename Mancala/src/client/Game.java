package client;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener {

    public static final int WIDTH = 750, HEIGHT = 300;
    public int PLAYER = 1, OPONENTE = -1, CURRENT = PLAYER;

    public BufferedImage TABULEIRO_0, TABULEIRO_1, TABULEIRO_2, TABULEIRO_3, TABULEIRO_4, TABULEIRO_5, TABULEIRO_6,
            TABULEIRO_7, TABULEIRO_8, TABULEIRO_9, TABULEIRO_10, TABULEIRO_11, TABULEIRO_12, TABULEIRO_13, TABULEIRO_14,
            TABULEIRO_15, TABULEIRO_16, TABULEIRO_17, TABULEIRO_18, TABULEIRO_19;
    public static int[][] TABULEIRO = new int[7][2];

    public static int mouse_x, mouse_y;

    public boolean pressed = false;
    public static int turns = 0;

    public Game() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.initSprites();
        resetTabuleiro();
    }

    public void initSprites() {
        try {
            TABULEIRO_0 = ImageIO.read(new File("Mancala/res/tabuleiro_0.png"));
            TABULEIRO_1 = ImageIO.read(new File("Mancala/res/tabuleiro_1.png"));
            TABULEIRO_2 = ImageIO.read(new File("Mancala/res/tabuleiro_2.png"));
            TABULEIRO_3 = ImageIO.read(new File("Mancala/res/tabuleiro_3.png"));
            TABULEIRO_4 = ImageIO.read(new File("Mancala/res/tabuleiro_4.png"));
            TABULEIRO_5 = ImageIO.read(new File("Mancala/res/tabuleiro_5.png"));
            TABULEIRO_6 = ImageIO.read(new File("Mancala/res/tabuleiro_6.png"));
            TABULEIRO_7 = ImageIO.read(new File("Mancala/res/tabuleiro_7.png"));
            TABULEIRO_8 = ImageIO.read(new File("Mancala/res/tabuleiro_8.png"));
            TABULEIRO_9 = ImageIO.read(new File("Mancala/res/tabuleiro_9.png"));
            TABULEIRO_10 = ImageIO.read(new File("Mancala/res/tabuleiro_10.png"));
            TABULEIRO_11 = ImageIO.read(new File("Mancala/res/tabuleiro_11.png"));
            TABULEIRO_12 = ImageIO.read(new File("Mancala/res/tabuleiro_12.png"));
            TABULEIRO_13 = ImageIO.read(new File("Mancala/res/tabuleiro_13.png"));
            TABULEIRO_14 = ImageIO.read(new File("Mancala/res/tabuleiro_14.png"));
            TABULEIRO_15 = ImageIO.read(new File("Mancala/res/tabuleiro_15.png"));
            TABULEIRO_16 = ImageIO.read(new File("Mancala/res/tabuleiro_16.png"));
            TABULEIRO_17 = ImageIO.read(new File("Mancala/res/tabuleiro_17.png"));
            TABULEIRO_18 = ImageIO.read(new File("Mancala/res/tabuleiro_18.png"));
            TABULEIRO_19 = ImageIO.read(new File("Mancala/res/tabuleiro_19.png"));
        } catch (IOException e) {
            System.err.println("[Game:initSprites] -> " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void resetTabuleiro() {
        for (int xx = 0; xx < 7; xx++) {
            for (int yy = 0; yy < 2; yy++) {
                TABULEIRO[xx][yy] = 4;
            }
        }
    }

    public int checkVictory() {
        return -10;
    }

    public void tick() {
        if (CURRENT == PLAYER) {

            if (pressed) {
                pressed = false;
                mouse_x /= 100;
                mouse_y /= 100;
                if (TABULEIRO[mouse_x][mouse_y] > 0) {

                    for(int i = TABULEIRO[mouse_x][mouse_y]; i > 0 ; i--){
                    }

                    TABULEIRO[mouse_x][mouse_y] = 0;
                    CURRENT = OPONENTE;
                }
            }

        } else if (CURRENT == OPONENTE) {
            if (pressed) {
                pressed = false;
                mouse_x /= 100;
                mouse_y /= 100;
                if (TABULEIRO[mouse_x][mouse_y] > 0) {
                    TABULEIRO[mouse_x][mouse_y] = 0;
                    CURRENT = PLAYER;
                }
            }
        }

        if (checkVictory() == PLAYER) {
            System.out.println("Player ganhou");

        } else if (checkVictory() == OPONENTE) {
            System.out.println("Oponente ganhou");

        } else if (checkVictory() == 0) {
            System.out.println("Empate");

        }
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        for (int xx = 0; xx < 7; xx++) {
            for (int yy = 0; yy < 2; yy++) {
                g.setColor(Color.black);
                g.drawRect(xx * 100, yy * 100, 100, 100);
                switch (TABULEIRO[xx][yy]) {
                    case 0:
                        g.drawImage(TABULEIRO_0, xx * 100 + 25, yy * 100 + 25, 50, 50, null);
                        break;
                    case 1:
                        g.drawImage(TABULEIRO_1, xx * 100 + 25, yy * 100 + 25, 50, 50, null);
                        break;
                    case 2:
                        g.drawImage(TABULEIRO_2, xx * 100 + 25, yy * 100 + 25, 50, 50, null);
                        break;
                    case 3:
                        g.drawImage(TABULEIRO_3, xx * 100 + 25, yy * 100 + 25, 50, 50, null);
                        break;
                    case 4:
                        g.drawImage(TABULEIRO_4, xx * 100 + 25, yy * 100 + 25, 50, 50, null);
                        break;
                    case 5:
                        g.drawImage(TABULEIRO_5, xx * 100 + 25, yy * 100 + 25, 50, 50, null);
                        break;
                    case 6:
                        g.drawImage(TABULEIRO_6, xx * 100 + 25, yy * 100 + 25, 50, 50, null);
                        break;
                    case 7:
                        g.drawImage(TABULEIRO_7, xx * 100 + 25, yy * 100 + 25, 50, 50, null);
                        break;
                    case 8:
                        g.drawImage(TABULEIRO_8, xx * 100 + 25, yy * 100 + 25, 50, 50, null);
                        break;
                    case 9:
                        g.drawImage(TABULEIRO_9, xx * 100 + 25, yy * 100 + 25, 50, 50, null);
                        break;
                    case 10:
                        g.drawImage(TABULEIRO_10, xx * 100 + 25, yy * 100 + 25, 50, 50, null);
                        break;
                    case 11:
                        g.drawImage(TABULEIRO_11, xx * 100 + 25, yy * 100 + 25, 50, 50, null);
                        break;
                    case 12:
                        g.drawImage(TABULEIRO_12, xx * 100 + 25, yy * 100 + 25, 50, 50, null);
                        break;
                    case 13:
                        g.drawImage(TABULEIRO_13, xx * 100 + 25, yy * 100 + 25, 50, 50, null);
                        break;
                    case 14:
                        g.drawImage(TABULEIRO_14, xx * 100 + 25, yy * 100 + 25, 50, 50, null);
                        break;
                    case 15:
                        g.drawImage(TABULEIRO_15, xx * 100 + 25, yy * 100 + 25, 50, 50, null);
                        break;
                    case 16:
                        g.drawImage(TABULEIRO_16, xx * 100 + 25, yy * 100 + 25, 50, 50, null);
                        break;
                    case 17:
                        g.drawImage(TABULEIRO_17, xx * 100 + 25, yy * 100 + 25, 50, 50, null);
                        break;
                    case 18:
                        g.drawImage(TABULEIRO_18, xx * 100 + 25, yy * 100 + 25, 50, 50, null);
                        break;
                    case 19:
                        g.drawImage(TABULEIRO_19, xx * 100 + 25, yy * 100 + 25, 50, 50, null);
                        break;        
                    default:
                        break;
                }

            }
        }

        g.dispose();
        bs.show();
    }

    public static void main(String args[]) {
        Game game = new Game();
        JFrame frame = new JFrame("Tic Tac Toe");
        frame.add(game);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        new Thread(game).start();
    }

    @Override
    public void run() {

        while (true) {
            tick();
            render();
            try {
                Thread.sleep(1000 / 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressed = true;
        mouse_x = e.getX();
        mouse_y = e.getY();

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

}
