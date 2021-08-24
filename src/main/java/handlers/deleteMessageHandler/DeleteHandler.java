package handlers.deleteMessageHandler;

import java.util.List;

public class DeleteHandler {
    long chatId;
    int idStopCommand;
    List<Integer> listIdMessage;
    List<Integer> listIdPhoto;

    public DeleteHandler(long chatId, int idStopCommand, List<Integer> listIdMessage, List<Integer> listIdPhoto) {
        this.chatId = chatId;
        this.idStopCommand = idStopCommand;
        this.listIdMessage = listIdMessage;
        this.listIdPhoto = listIdPhoto;
    }
}
