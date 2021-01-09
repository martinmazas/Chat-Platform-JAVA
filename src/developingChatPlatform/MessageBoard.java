package developingChatPlatform;

import java.util.ArrayList;
import java.util.List;

public class MessageBoard implements StringConsumer, StringProducer {
    /**
     * Class with all the users that are connected. When the class receives a message the program send it to all the users
     */
    private ArrayList<ConnectionProxy> proxiesList ;

    public MessageBoard() {
       proxiesList = new ArrayList<>();
    }

    @Override
    public void consume(String str) {
        /**
         * Send the message for all the connected users. Every time check if the user is still connected to the chat.
         * If the user is not connected remove it from the proxies list
         */
        for (int i = 0; i < proxiesList.size(); i++) {
            if(!proxiesList.get(i).isAlive()){
                removeConsumer(proxiesList.get(i));
                i--;
            }
            else{
                proxiesList.get(i).consume(str);
            }
        }
    }

    @Override
    public void addConsumer(StringConsumer sc) {
        if(sc != null){
            proxiesList.add((ConnectionProxy)sc);
        }

    }

    @Override
    public void removeConsumer(StringConsumer sc) {
        proxiesList.remove(sc);
    }
}
