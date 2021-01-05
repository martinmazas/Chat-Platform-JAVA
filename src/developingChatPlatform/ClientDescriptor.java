package developingChatPlatform;

public class ClientDescriptor implements StringConsumer, StringProducer {
    private String nickname;
    private String text;
    StringConsumer consumer;

    @Override
    public void consume(String str) {

        text = nickname + " : " + str;
//        consumer.consume(text);
        System.out.println(text);
    }

    @Override
    public void addConsumer(StringConsumer sc) {
        consumer = sc;
    }

    @Override
    public void removeConsumer(StringConsumer sc) {

    }
}
