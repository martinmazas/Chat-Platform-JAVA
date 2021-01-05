package developingChatPlatform;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class DemoClientGUI {
    public static void main(String args[]) {
        ClientGUI client = new ClientGUI();
        client.run();
    }

}
