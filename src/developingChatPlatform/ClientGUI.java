package developingChatPlatform;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ClientGUI implements StringConsumer, StringProducer{
    /**
     * Login and chat panels for each user, login panel connects to chat panel and chat panel
     * connects to the other users
     */

    private StringConsumer consumer;
    private Socket socket = null;
    private ConnectionProxy proxy = null;

    /**
     * sign in components
     */
    private JFrame signInFrame;
    private JPanel signInPanel;
    private JLabel nicknameLabel;
    private JButton signInButton;
    private JTextField nicknameField;

    /**
     * chat components
     */
    private JFrame chatFrame;
    private JPanel chatPanelOne,chatPanelTwo;
    private JTextArea chatTextArea;
    private JTextField chatTextField;
    private JButton sendMessageButton;

    private JButton backButton;


    /**
     * Class constructor
     */
    public ClientGUI(){
        /**
         * Initialize the sign in components
         */
        signInFrame = new JFrame("Sign In");
        nicknameLabel = new JLabel("Nickname");
        signInPanel = new JPanel();
        signInButton = new JButton("Sign in");
        nicknameField = new JTextField();

        /**
         * Initialize the chat components
         */
        chatFrame = new JFrame("Chat");
        chatPanelOne = new JPanel();
        chatPanelTwo = new JPanel();
        chatTextArea = new JTextArea();
        chatTextField = new JTextField();
        sendMessageButton = new JButton("Send");

        backButton = new JButton("Back");
    }

    /**
     * running the class
     */
    public void run() {
        signIn();
    }

    /**
     * Initialize the sign in part
     */
    public void signIn() {
        signInFrame.setSize(600,250);
        signInFrame.setLayout(new BorderLayout());
        signInPanel.setLayout(new GridLayout(2,2));
        signInPanel.add(nicknameLabel);
        signInPanel.add(nicknameField);
        signInPanel.add(backButton);
        signInPanel.add(signInButton);

        signInFrame.add(signInPanel);
        signInFrame.setVisible(true);

        /**
         * By clicking the sign in button, the program sends the nickname to the start chat function
         */
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!nicknameField.getText().equals("")){
                    String nickname = nicknameField.getText();
                    signInFrame.setVisible(false);
                    startChat(nickname);
                }
            }
        });

        /**
         * By closing the window the program end the system
         */
        signInFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * Starting the chat frame.
     */
    public void startChat(String nickname) {
        /**
         * Creates a proxy to send the nickname to the client descriptor. The new consumer will be the proxy.
         */
        try{
            socket = new Socket("127.0.0.1", 1300);
            proxy = new ConnectionProxy(socket);
            addConsumer(proxy);
            proxy.addConsumer(this);
            proxy.consume(nickname);
            proxy.start();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        chatFrame.setSize(600, 600);
        chatFrame.setLayout(new BorderLayout());
        chatPanelOne.setLayout(new BorderLayout());
        chatTextArea.setEditable(false);
        chatTextArea.setAutoscrolls(true);
        chatPanelOne.add(chatTextArea);
        chatFrame.add(chatPanelOne, BorderLayout.CENTER);

        chatPanelTwo.setLayout(new GridLayout(1,2));
        chatPanelTwo.add(chatTextField);
        chatPanelTwo.add(sendMessageButton);
        chatFrame.add(chatPanelTwo, BorderLayout.SOUTH);


        chatFrame.setVisible(true);

        /**
         * By clicking the send button the text will be send it to the other users
         * */
        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!chatTextField.getText().equals("")){
                    consumer.consume(chatTextField.getText());
                    chatTextField.setText("");
                }
            }
        });


        /**
         * Closing the window the program close the socket and end the system
         */
        chatFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    consumer.consume("Disconnect");
                    socket.close();
                    removeConsumer(consumer);
                    closeComponents();
                    System.exit(0);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    public void closeComponents() {
        signInFrame = null;
        signInPanel = null;
        nicknameLabel = null;
        signInButton = null;
        nicknameField = null;

        chatFrame = null;
        chatPanelOne = null;
        chatPanelTwo = null;
        chatTextArea = null;
        chatTextField = null;
        sendMessageButton = null;

    }


    @Override
    public void consume(String str) {
        chatTextArea.append(str + "\n");
    }

    @Override
    public void addConsumer(StringConsumer sc) {
        consumer = sc;
    }

    @Override
    public void removeConsumer(StringConsumer sc) {
        consumer = null;
    }
}
