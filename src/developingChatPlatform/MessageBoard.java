package developingChatPlatform;

import java.util.List;

public class MessageBoard implements StringConsumer, StringProducer {
    private List<StringConsumer> consumersList;

    @Override
    public void consume(String str) {
        for(int i = 0; i<consumersList.size(); i ++) {
            consumersList.get(i).consume(str);
        }
    }

    @Override
    public void addConsumer(StringConsumer sc) {
        consumersList.add(sc);
    }

    @Override
    public void removeConsumer(StringConsumer sc) {
        consumersList.remove(sc);
    }
}
