package handlers.messageHandler;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

public class SendPhotoCallbackHandler extends SendPhotoUpdateHandler {
    public SendPhotoCallbackHandler(long chatId, List<Integer> listIdPhoto) {
        super(chatId, listIdPhoto);
    }


    public SendPhoto forwardPhoto(String pathPhoto, String captionPhoto, CallbackQuery callbackQuery) {
        listIdPhoto.add(callbackQuery.getMessage().getMessageId());
        return super.forwardPhoto(pathPhoto, captionPhoto);
    }
}
