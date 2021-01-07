package developingChatPlatform;

public class ClientDescriptor implements StringConsumer, StringProducer {
    private String nickname = "";
    private String text;
    StringConsumer consumer;

    @Override
    public void consume(String str) {
        if (nickname.equals("")) {
            nickname = str;
        }
        else{
            text = nickname + " says: " + str;
            consumer.consume(text);
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
