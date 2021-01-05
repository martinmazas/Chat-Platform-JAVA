package developingChatPlatform;

import java.io.*;
import java.net.Socket;


public class ConnectionProxy extends Thread implements StringConsumer, StringProducer{

    private Socket socket = null;
    private StringConsumer consumer;


    public ConnectionProxy(Socket socket) {
        System.out.println(socket);
    }

    public void run() {

    }

    @Override
    public void consume(String str) {
        ClientDescriptor descriptor = new ClientDescriptor();
        descriptor.consume(str);
    }

    @Override
    public void addConsumer(StringConsumer sc) {
        consumer = sc;
    }

    @Override
    public void removeConsumer(StringConsumer sc) {

    }
}
