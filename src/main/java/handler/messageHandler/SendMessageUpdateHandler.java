package handler.messageHandler;

import handler.messageHandler.SendMessageHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class SendMessageUpdateHandler extends SendMessageHandler {

    public SendMessageUpdateHandler(long chatId, List<Integer> listMessage) {
        super(chatId, listMessage);
    }


    public SendMessage forwardMessage(String text, Update update) {
        listMessage.add(update.getMessage().getMessageId());
        return super.forwardMessage(text);
    }
}
