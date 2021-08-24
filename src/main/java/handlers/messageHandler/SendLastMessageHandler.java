package handlers.messageHandler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class SendLastMessageHandler extends SendMessageHandler{
    public SendLastMessageHandler(long chatId) {
        super(chatId);
    }

    public SendMessage forwardLastMessage(String text) {
        return super.forwardMessage(text);
    }
}
