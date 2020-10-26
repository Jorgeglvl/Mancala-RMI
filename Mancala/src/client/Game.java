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

public class Game extends Canvas implements Runnable,KeyListener,MouseListener{

	
	public static final int WIDTH = 300, HEIGHT = 300;
	public int PLAYER = 1,OPONENTE = -1,CURRENT = PLAYER;
	
	public BufferedImage PLAYER_SPRITE,OPONENTE_SPRITE;
	public static int[][] TABULEIRO = new int[3][3];
	
	public static int mouse_x,mouse_y;

	public boolean pressed = false;
	public static int turns = 0;
	
	public Game() {
		this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		this.addKeyListener(this);
		this.addMouseListener(this);
		try {
			PLAYER_SPRITE = ImageIO.read(new File("Mancala/res/player.png"));
			OPONENTE_SPRITE = ImageIO.read(new File("Mancala/res/oponente.png"));
		} catch (IOException e) {
            System.err.println("[Game:Game] -> " + e.getMessage());
			e.printStackTrace();
		}
		resetTabuleiro();
	}
	
	public void resetTabuleiro() {
		for(int xx = 0; xx < TABULEIRO.length; xx++) {
			for(int yy = 0; yy < TABULEIRO.length; yy++) {
				TABULEIRO[xx][yy] = 0;
			}
		}
	}
	
	public int checkVictory() {		
		//Verificar se o OPONENTE ganhou
		if(TABULEIRO[0][0] == OPONENTE && TABULEIRO[1][0] == OPONENTE && TABULEIRO[2][0] == OPONENTE) {
			//O player ganhou
			return OPONENTE;
		}
		if(TABULEIRO[0][1] == OPONENTE && TABULEIRO[1][1] == OPONENTE && TABULEIRO[2][1] == OPONENTE) {
			//O player ganhou
			return OPONENTE;
		}
		if(TABULEIRO[0][2] == OPONENTE && TABULEIRO[1][2] == OPONENTE && TABULEIRO[2][2] == OPONENTE) {
			//O player ganhou
			return OPONENTE;
		}
		
		//Vertical
		
		if(TABULEIRO[0][0] == OPONENTE && TABULEIRO[0][1] == OPONENTE && TABULEIRO[0][2] == OPONENTE) {
			//O player ganhou
			return OPONENTE;
		}
		if(TABULEIRO[1][0] == OPONENTE && TABULEIRO[1][1] == OPONENTE && TABULEIRO[1][2] == OPONENTE) {
			//O player ganhou
			return OPONENTE;
		}
		if(TABULEIRO[2][0] == OPONENTE && TABULEIRO[2][1] == OPONENTE && TABULEIRO[2][2] == OPONENTE) {
			//O player ganhou
			return OPONENTE;
		}
		
		//Diagonal
		if(TABULEIRO[0][0] == OPONENTE && TABULEIRO[1][1] == OPONENTE && TABULEIRO[2][2] == OPONENTE) {
			//O player ganhou
			return OPONENTE;
		}
		if(TABULEIRO[2][0] == OPONENTE && TABULEIRO[1][1] == OPONENTE && TABULEIRO[0][2] == OPONENTE) {
			//O player ganhou
			return OPONENTE;
		}
		
		//Verificar se o player ganhou
		if(TABULEIRO[0][0] == PLAYER && TABULEIRO[1][0] == PLAYER && TABULEIRO[2][0] == PLAYER) {
			//O player ganhou
			return PLAYER;
		}
		if(TABULEIRO[0][1] == PLAYER && TABULEIRO[1][1] == PLAYER && TABULEIRO[2][1] == PLAYER) {
			//O player ganhou
			return PLAYER;
		}
		if(TABULEIRO[0][2] == PLAYER && TABULEIRO[1][2] == PLAYER && TABULEIRO[2][2] == PLAYER) {
			//O player ganhou
			return PLAYER;
		}
		
		//Vertical
		
		if(TABULEIRO[0][0] == PLAYER && TABULEIRO[0][1] == PLAYER && TABULEIRO[0][2] == PLAYER) {
			//O player ganhou
			return PLAYER;
		}
		if(TABULEIRO[1][0] == PLAYER && TABULEIRO[1][1] == PLAYER && TABULEIRO[1][2] == PLAYER) {
			//O player ganhou
			return PLAYER;
		}
		if(TABULEIRO[2][0] == PLAYER && TABULEIRO[2][1] == PLAYER && TABULEIRO[2][2] == PLAYER) {
			//O player ganhou
			return PLAYER;
		}
		
		//Diagonal
		if(TABULEIRO[0][0] == PLAYER && TABULEIRO[1][1] == PLAYER && TABULEIRO[2][2] == PLAYER) {
			//O player ganhou
			return PLAYER;
		}
		if(TABULEIRO[2][0] == PLAYER && TABULEIRO[1][1] == PLAYER && TABULEIRO[0][2] == PLAYER) {
			//O player ganhou
			return PLAYER;
		}
		
		int curScore = 0;
		//Empate
		for(int xx =0; xx < TABULEIRO.length; xx++) {
			for(int yy = 0; yy < TABULEIRO.length; yy++) {
				if(TABULEIRO[xx][yy] != 0) {
					curScore++;
				}
			}
		}		
		
		if(curScore == TABULEIRO.length * TABULEIRO[0].length) {
			return 0;
		}

		//Ninguém ganhou
		return -10;
	}

	public void tick() {
		if(CURRENT == PLAYER) {
			
			if(pressed) {
				pressed = false;
				mouse_x/=100;
				mouse_y/=100;
				if(TABULEIRO[mouse_x][mouse_y] == 0) {
					TABULEIRO[mouse_x][mouse_y] = PLAYER;
					CURRENT = OPONENTE;
				}
			}			

		}else if(CURRENT == OPONENTE){
			if(pressed) {
				pressed = false;
				mouse_x/=100;
				mouse_y/=100;
				if(TABULEIRO[mouse_x][mouse_y] == 0) {
					TABULEIRO[mouse_x][mouse_y] = OPONENTE;
					CURRENT = PLAYER;
				}
			}			
		}
		
		if(checkVictory() == PLAYER) {
			System.out.println("Player ganhou");
			
		}else if(checkVictory() == OPONENTE) {
			System.out.println("Oponente ganhou");
			
		}else if(checkVictory() == 0) {
			System.out.println("Empate");
			
		}
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, WIDTH,HEIGHT);
		
		for(int xx = 0; xx < TABULEIRO.length; xx++) {
			for(int yy = 0; yy < TABULEIRO.length; yy++) {
				g.setColor(Color.black);
				g.drawRect(xx*100, yy*100, 100,100);
				if(TABULEIRO[xx][yy] == PLAYER) {
					g.drawImage(PLAYER_SPRITE,xx*100 + 25, yy*100 + 25,50,50,null);
				}else if(TABULEIRO[xx][yy] == OPONENTE) {
					g.drawImage(OPONENTE_SPRITE,xx*100 + 25, yy*100 + 25,50,50,null);
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
		
		while(true) {
			tick();
			render();
			try {
				Thread.sleep(1000/60);
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

