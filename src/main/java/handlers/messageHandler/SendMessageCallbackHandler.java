package handlers.messageHandler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

public class SendMessageCallbackHandler extends SendMessageUpdateHandler {


    public SendMessageCallbackHandler(long chatId, List<Integer> listIdMessage) {
        super(chatId, listIdMessage);
    }

    public SendMessage forwardMessage(String text, CallbackQuery callbackQuery) {
        listIdMessage.add(callbackQuery.getMessage().getMessageId());
        return super.forwardMessage(text);
    }
}
