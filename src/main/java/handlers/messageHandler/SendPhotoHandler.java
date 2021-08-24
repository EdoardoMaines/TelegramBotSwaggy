package handlers.messageHandler;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.File;

public class SendPhotoHandler{
    long chatId;

    public SendPhotoHandler(long chatId) {
        this.chatId = chatId;

    }

    public SendPhoto forwardPhoto(String pathPhoto, String captionPhoto) {
        SendPhoto message = new SendPhoto();
        message.setCaption(captionPhoto);
        message.setPhoto(new InputFile(new File(pathPhoto)));
        message.setChatId(String.valueOf(chatId));
        return message;
    }
}
