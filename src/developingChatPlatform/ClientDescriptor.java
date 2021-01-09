package developingChatPlatform;

public class ClientDescriptor implements StringConsumer, StringProducer {
    /**
     * For each user the class save his nickname and send to message board class the information that the class need
     * to send to the other users
     */
    private String nickname = "";
    private String text;
    StringConsumer consumer;

    @Override
    public void consume(String str) {
        /**
         * If the nickname is empty the user is accessing to the system by first time so he send to all the users
         * that he has joined.
         * If the class received the word Disconnect its because the user is getting out of the system so the program
         * send to the rest of the users that the user has left the conversation
         * Else send to message board class the text he want to send.
         */
        if (nickname.equals("")) {
            nickname = str;
            consumer.consume(nickname + " has joined the conversation");
        }
        else if(str.equals("Disconnect")) {
            consumer.consume(nickname + " has left the conversation");
            removeConsumer(consumer);
            nickname = "";
        }
        else {
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
