package client;
import javax.swing.JOptionPane;

public class Notify {

	public static String configHost() {
		
		String host;
		
		host = JOptionPane.showInputDialog("Qual o ip do servidor? (Padrão: localhost)");
		if(host==null||host.contentEquals("")) {
			return "localhost";
		}
		else {
			return host;
		}
	}
	
	public static int configuraPorta() {
		
		String porta;
		
		porta = JOptionPane.showInputDialog("Qual a porta do servidor? (Padrão: 9090)");
		if(porta==null||porta.contentEquals("")) {
			return 9090;
		}
		else {
			return Integer.parseInt(porta);
		}
	}
	
	public static void desistir(int jogador) {
		JOptionPane.showMessageDialog(null, "Jogador "+(jogador+1)+" desistiu");
	}
	
	public static int confirmaDesistencia() {
		return JOptionPane.showConfirmDialog(null, "Tem certeza?","Desistencia",JOptionPane.YES_NO_OPTION);
	}
	
	public static void saida(int jogador) {
		JOptionPane.showMessageDialog(null, "Jogador "+(jogador+1)+" saiu");
	}
	
	public static int confirmaJogarNovamente() {
		return JOptionPane.showConfirmDialog(null, "Deseja jogar novamente?","Revanche",JOptionPane.YES_NO_OPTION);
	}
	
	public static void pendencia() {
		JOptionPane.showMessageDialog(null, "Não há jogadores para jogar, o jogo será encerrado");
	}
	
	public static void vitoria() {
		JOptionPane.showMessageDialog(null, "Você venceu");
	}
	
	public static void derrota() {
		JOptionPane.showMessageDialog(null, "Você perdeu");
	}
	
	public static void empate() {
		JOptionPane.showMessageDialog(null, "Empate!");
	}
}
