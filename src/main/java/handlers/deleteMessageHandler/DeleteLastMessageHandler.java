package handlers.deleteMessageHandler;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;

import java.util.List;

public class DeleteLastMessageHandler extends DeleteHandler {
    public DeleteLastMessageHandler(long chatId, int idStopCommand, List<Integer> listIdMessage, List<Integer> listIdPhoto) {
        super(chatId, idStopCommand, listIdMessage, listIdPhoto);
    }

    public DeleteMessage deleteLastMessage(int delay) {
        DeleteMessage deleteMessage = new DeleteMessage(String.valueOf(chatId), idStopCommand+1);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return deleteMessage;
    }
}
