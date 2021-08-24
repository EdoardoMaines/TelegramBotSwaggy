package handlers.messageHandler;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Getter
public class SendMessageUpdateHandler extends SendMessageHandler {
    List<Integer> listIdMessage;

    public SendMessageUpdateHandler(long chatId, List<Integer> listIdMessage) {
        super(chatId);
        this.listIdMessage = listIdMessage;
    }


    public SendMessage forwardMessage(String text, Update update) {
        listIdMessage.add(update.getMessage().getMessageId());
        return super.forwardMessage(text);
    }
}
