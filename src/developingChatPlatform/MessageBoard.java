package developingChatPlatform;

import java.util.ArrayList;

public class MessageBoard implements StringConsumer, StringProducer {
    private ArrayList<ConnectionProxy> proxiesList ;

    public MessageBoard() {
       proxiesList = new ArrayList<>();
    }

    @Override
    public void consume(String str) {
        for(int i = 0; i < proxiesList.size(); i ++) {
            proxiesList.get(i).consume(str);
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
