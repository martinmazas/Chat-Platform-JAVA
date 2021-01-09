package developingChatPlatform;

import java.io.*;
import java.net.Socket;



public class ConnectionProxy extends Thread implements StringConsumer, StringProducer{
    /**
     * For each client it would be one proxy. This class let the program connect the client with the server side
     */

    private Socket socket;
    private StringConsumer consumer;
    private DataOutputStream dos;
    private DataInputStream dis;


    /**
     * Class Constructor
     */
    public ConnectionProxy(Socket socket) throws IOException {
        this.socket = socket;
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
       try {
           while(!socket.isClosed()) {
               String received = dis.readUTF();
               consumer.consume(received);
               if (received.equals("Disconnect")) {
                   socket.close();
                   break;
               }
           }
           removeConsumer(consumer);
       } catch (IOException exception) {
           exception.printStackTrace();
       }
    }

    @Override
    public void consume(String str) {
        try {
            dos.writeUTF(str);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
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
