package client;

import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;

import server.*;

public class ChatFrame extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JLabel jl_title;
    private JEditorPane messages;
    private JTextField jt_message;
    private JButton jb_message;
    private JPanel panel;
    private JScrollPane scroll;

    private Game game;
    private boolean player_type;
    private String title;
    private ArrayList<String> message_list;

    public ChatFrame(Game game, boolean player_type) {
        super("Chat Mancala");
        this.game = game;
        this.player_type = player_type;
        if (this.player_type) {
            this.title = "Jogador 1";
        } else {
            this.title = "Jogador 2";
        }
        initComponents();
        configComponents();
        insertComponents();
        insertActions();
        start();
    }

    private void initComponents(){
        jl_title = new JLabel("Você é o " + this.title, SwingConstants.CENTER);
        message_list = new ArrayList<String>();        
        messages = new JEditorPane();
        scroll = new JScrollPane(messages);
        jt_message = new JTextField();
        jb_message = new JButton("Enviar");
        panel = new JPanel(new BorderLayout());

    }

    private void configComponents(){
        this.setMinimumSize(new Dimension(480, 720));
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        messages.setContentType("text/html");
        messages.setEditable(false);

        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        jb_message.setSize(100, 40);
    }

    private void insertComponents(){
        this.add(jl_title, BorderLayout.NORTH);
        this.add(scroll, BorderLayout.CENTER);
        this.add(panel, BorderLayout.SOUTH);
        panel.add(jt_message, BorderLayout.CENTER);
        panel.add(jb_message, BorderLayout.EAST);
    } 
    
    private void insertActions(){
        jb_message.addActionListener(event -> send());
        jt_message.addKeyListener(new KeyListener(){
            public void keyTyped(KeyEvent e){

            }
            public void keyPressed(KeyEvent e){
                if(e.getKeyChar() == KeyEvent.VK_ENTER){
                    send();
                }
            }
            public void keyReleased(KeyEvent e){

            }
        });

        this.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowClosing(WindowEvent e) {
                onClose();
                dispose();
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

    public void append_message(String received){
        message_list.add(received);
        String message = "";
        for(String str : message_list){
            message += str;
        }
        messages.setText(message);
    }

    private void send(){
        if(jt_message.getText().length() > 0){
            SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
            try {
                this.game.enemy.updateChat("<b>[" + df.format(new Date()) + "] " + this.title + ": </b><i>" + jt_message.getText() + "</i><br>");
            } catch (RemoteException e) {
                JOptionPane.showMessageDialog(null, "" + e.getMessage());
                e.printStackTrace();
            }
            append_message("<b>[" + df.format(new Date()) + "] Voce: </b><i>" + jt_message.getText() + "</i><br>");
            jt_message.setText("");
        }
        
    }

    private void onClose(){
        SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
        try {
            this.game.enemy.updateChat("<b>[" + df.format(new Date()) + "] O " + this.title + " <i>deixou o chat.</i></b><br>");
        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(null, "" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void start(){
        this.pack();
        this.setVisible(true);
    }
}
