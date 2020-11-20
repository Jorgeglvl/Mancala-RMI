package client;

import java.rmi.RemoteException;

import java.awt.*;
import javax.swing.*;
import java.io.IOException;

public class LoginRMI extends JFrame {

    private JButton jb_login;
    private JLabel jl_user, jl_port, jl_title;
    private JTextField jt_ip, jt_port;

    public LoginRMI() throws IOException {
        super("login");
        initComponents();
        configComponents();
        insertComponents();
        insertActions();
        start();
    }

    private void initComponents(){
        jb_login = new JButton("Entrar");
        jl_user = new JLabel("Ip", SwingConstants.CENTER);
        jl_port = new JLabel("Porta", SwingConstants.CENTER);
        jl_title = new JLabel("");
        jt_ip = new JTextField("127.0.0.1");
        jt_port = new JTextField("4444");

        jl_title.setBounds(10, 10, 375, 100);
        ImageIcon icon = new ImageIcon(getClass().getResource("/logo.png"));
        jl_title.setIcon(new ImageIcon(icon.getImage().getScaledInstance(365, 100,  Image.SCALE_SMOOTH)));

        jb_login.setBounds(10, 220, 375, 40);

        jl_user.setBounds(10, 120, 100, 40);
        jl_user.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        jl_user.setForeground(Color.ORANGE);
    
        jl_port.setBounds(10, 170, 100, 40);
        jl_port.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        jl_port.setForeground(Color.ORANGE);


        jt_ip.setBounds(120, 120, 265, 40);
        jt_port.setBounds(120, 170, 265, 40);
    }

    private void configComponents(){
        this.setLayout(null);
        this.setMinimumSize(new Dimension(400, 300));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.BLACK);
    }

    private void insertComponents(){
        this.add(jb_login);
        this.add(jl_user);
        this.add(jl_port);
        this.add(jl_title);
        this.add(jt_ip);
        this.add(jt_port);

    } 
    
    private void insertActions() throws IOException {
        jb_login.addActionListener(event -> {
            String ip = jt_ip.getText();
            jt_ip.setText("127.0.0.1");
            int port = Integer.parseInt(jt_port.getText());
            jt_port.setText("4444");

            try {
                new Player(ip, port);
            } catch (RemoteException e) {
                JOptionPane.showMessageDialog(null, "" + e.getMessage());
                
                e.printStackTrace();
            }
            this.dispose();
        });
    }

    private void start(){
        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        LoginRMI login = new LoginRMI();
    }
    
}
