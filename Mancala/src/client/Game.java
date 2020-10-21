package client;

import java.awt.*;
import java.awt.event.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;

public class Game extends JFrame{

    private JLabel jl_title;
    private JEditorPane messages;
    private JTextField jt_message;
    private JButton jb_message;
    private JPanel panel;
    private JScrollPane scroll;

    private Home home;
    private Socket connection;
    private String connection_info;
    private ArrayList<String> message_list;

    public Game(Home home, Socket connection, String connection_info, String title){
        super("Mancala " + title);
        this.connection_info = connection_info;
        this.home = home;
        this.connection = connection;
        initComponents();
        configComponents();
        insertComponents();
        insertActions();
        start();
    }

    private void initComponents(){
        message_list = new ArrayList<String>();
        jl_title = new JLabel(connection_info.split(":")[0], SwingConstants.CENTER);
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
    }

    public void apend_message(String received){
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
            apend_message("<b>[" + df.format(new Date()) + "] Voce: </b><i>" + jt_message.getText() + "</i><br>");
            jt_message.setText("");
        }
        
    }

    private void start(){
        this.pack();
        this.setVisible(true);
    }
}
