package developingChatPlatform;

import java.io.*;
import java.net.Socket;


public class ConnectionProxy extends Thread implements StringConsumer, StringProducer{

    private Socket socket = null;
    private StringConsumer consumer;
    private DataOutputStream dos = null;
    private DataInputStream dis = null;


    /**
     * Class Constructor
     * @param socket
     * @throws IOException
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
               if(received.equals("Disconnect")){
                   socket.close();
                   break;
               }
               consumer.consume(received);
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
