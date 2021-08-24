package handlers.deleteMessageHandler;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;

import java.util.ArrayList;
import java.util.List;

public class DeleteAllMessagesHandler extends DeleteHandler {

    List<DeleteMessage> listDeleteMessage;

    public DeleteAllMessagesHandler(long chatId, int idStopCommand, List<Integer> listIdMessage, List<Integer> listIdPhoto) {
        super(chatId, idStopCommand, listIdMessage, listIdPhoto);
        listDeleteMessage = new ArrayList<>();
    }

    public List<DeleteMessage> deleteAllChat() {

        for (int i=idStopCommand; i>=idStopCommand - (listIdMessage.size()+listIdPhoto.size()); i--) {
            DeleteMessage deleteMessage = new DeleteMessage(String.valueOf(chatId), i);
            listDeleteMessage.add(deleteMessage);
        }
        listIdMessage.clear();
        listIdPhoto.clear();
        return listDeleteMessage;
    }
}
