package developingChatPlatform;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;


public class ClientGUI implements StringConsumer, StringProducer{
    private String nickname= null;
    private StringConsumer consumer;

    //Sign in components
    private JFrame signInFrame;
    private JPanel signInPanel;
    private JLabel nicknameLabel;
    private JButton signInButton;
    private JTextField nicknameField;

    //Chat components
    private JFrame chatFrame;
    private JPanel chatPanelOne,chatPanelTwo;
    private JTextArea chatTextArea;
    private JTextField chatTextField;
    private JButton sendMessageButton;

    private JButton backButton;


    public ClientGUI(){
        //Sign in constructor
        signInFrame = new JFrame("Sign In");
        nicknameLabel = new JLabel("Nickname");
        signInPanel = new JPanel();
        signInButton = new JButton("Sign in");
        nicknameField = new JTextField();

        //Chat constructor
        chatFrame = new JFrame("Chat");
        chatPanelOne = new JPanel();
        chatPanelTwo = new JPanel();
        chatTextArea = new JTextArea();
        chatTextField = new JTextField();
        sendMessageButton = new JButton("Send");

        backButton = new JButton("Back");
    }

    public void signIn() {
        signInFrame.setSize(600,250);
        signInPanel.setBackground(Color.orange);
        signInFrame.setLayout(new BorderLayout());
        signInPanel.setLayout(new GridLayout(2,2));
        signInPanel.add(nicknameLabel);
        signInPanel.add(nicknameField);
        signInPanel.add(backButton);
        signInPanel.add(signInButton);

        signInFrame.add(signInPanel);
        signInFrame.setVisible(true);

        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!nicknameField.getText().equals("")){
                    setNickname(nicknameField.getText());
                    signInFrame.setVisible(false);
                    startChat(getNickname());
                }
            }
        });

        signInFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void startChat(String nickname) {
        chatFrame.setSize(600, 600);
        chatFrame.setLayout(new BorderLayout());

        chatPanelOne.setBackground(Color.YELLOW);
        chatPanelOne.setLayout(new GridLayout(10,1));
        chatPanelOne.add(chatTextArea);
        chatFrame.add(chatPanelOne, BorderLayout.CENTER);

        chatPanelTwo.setBackground(Color.CYAN);
        chatPanelTwo.setLayout(new GridLayout(1,2));
        chatPanelTwo.add(chatTextField);
        chatPanelTwo.add(sendMessageButton);
        chatFrame.add(chatPanelTwo, BorderLayout.SOUTH);


        chatFrame.setVisible(true);

        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!chatTextField.getText().equals("")){
                    InputStream is = null;
                    OutputStream os = null;
                    DataInputStream dis = null;
                    DataOutputStream dos = null;
                    try{
                        Socket socket = new Socket("127.0.0.1", 1300);
                        ConnectionProxy proxy = new ConnectionProxy(socket);
                        proxy.consume(chatTextField.getText());
//                        addConsumer(proxy);

                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });

        chatFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void run() {
        signIn();
//        startChat("Martin");
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    @Override
    public void consume(String str) {
        consumer.consume(str);
    }

    @Override
    public void addConsumer(StringConsumer sc) {
        consumer = sc;
    }

    @Override
    public void removeConsumer(StringConsumer sc) {
        System.out.println("removeConsumer");
    }
}
