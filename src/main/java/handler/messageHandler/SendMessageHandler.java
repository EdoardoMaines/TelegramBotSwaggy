package handler.messageHandler;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public class SendMessageHandler {
    long chatId;


    public SendMessageHandler(long chatId) {
        this.chatId = chatId;
    }

    public SendMessage forwardMessage(String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        return message;
    }
}
