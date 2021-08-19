package handler.messageHandler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public class SendMessageHandler {
    long chatId;
    List<Integer> listMessage;

    public SendMessageHandler(long chatId, List<Integer> listMessage) {
        this.chatId = chatId;
        this.listMessage = listMessage;
    }

    public SendMessage forwardMessage(String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        return message;
    }
}
