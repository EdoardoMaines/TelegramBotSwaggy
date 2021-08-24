package handlers.messageHandler;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Getter
public class SendPhotoUpdateHandler extends SendPhotoHandler{

    List<Integer> listIdPhoto;

    public SendPhotoUpdateHandler(long chatId, List<Integer> listIdPhoto) {
        super(chatId);
        this.listIdPhoto = listIdPhoto;
    }

    public SendPhoto forwardPhoto(String pathPhoto, String captionPhoto, Update update) {
        listIdPhoto.add(update.getMessage().getMessageId());
        return super.forwardPhoto(pathPhoto, captionPhoto);
    }
}
