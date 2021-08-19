package handler.messageHandler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

public class SendMessageCallbackHandler extends SendMessageHandler {


    public SendMessageCallbackHandler(long chatId, List<Integer> listMessage) {
        super(chatId, listMessage);
    }

    public SendMessage forwardMessage(String text, CallbackQuery callbackQuery) {
        listMessage.add(callbackQuery.getMessage().getMessageId());
        return super.forwardMessage(text);
    }
}
