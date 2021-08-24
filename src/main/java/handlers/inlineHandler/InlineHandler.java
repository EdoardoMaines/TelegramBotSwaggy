package handlers.inlineHandler;

import handlers.messageHandler.SendMessageCallbackHandler;
import handlers.messageHandler.SendMessageUpdateHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InlineHandler {

    long chatId;
    String user;
    List<Integer> listIdMessage;

    public InlineHandler(long chatId, String user, List<Integer> listIdMessage) {
        this.chatId = chatId;
        this.user = user;
        this.listIdMessage = listIdMessage;
    }

    public SendMessage sendInline (Update update){

        String user = update.getMessage().getFrom().getUserName();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText("Info BTC");
        inlineKeyboardButton1.setCallbackData("Info BTC");
        inlineKeyboardButton2.setText("Macchine Noleggiate");
        inlineKeyboardButton2.setCallbackData("Macchine Noleggiate");
        inlineKeyboardButton3.setText("Swaggy");
        inlineKeyboardButton3.setCallbackData("Swaggy");

        List<InlineKeyboardButton> rowList = new ArrayList<>();
        rowList.add(inlineKeyboardButton1);
        rowList.add(inlineKeyboardButton2);
        rowList.add(inlineKeyboardButton3);

        inlineKeyboardMarkup.setKeyboard(Collections.singletonList(rowList));

        SendMessageUpdateHandler sendMessageUpdateHandler = new SendMessageUpdateHandler(chatId, listIdMessage);
        SendMessage message = sendMessageUpdateHandler.forwardMessage("Ciao " + user + "! Seleziona cosa di cui hai bisogno.", update);
//        message.setText("Ciao " + user + "! Seleziona cosa di cui hai bisogno.");
//        message.setChatId(String.valueOf(chatId));
        message.setReplyMarkup(inlineKeyboardMarkup);

        //listMessage.add(update.getMessage().getMessageId());

        return message;
    }
    public SendMessage sendBackInline (CallbackQuery callbackQuery){

        String user = callbackQuery.getFrom().getUserName();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText("Info BTC");
        inlineKeyboardButton1.setCallbackData("Info BTC");
        inlineKeyboardButton2.setText("Macchine Noleggiate");
        inlineKeyboardButton2.setCallbackData("Macchine Noleggiate");
        inlineKeyboardButton3.setText("Swaggy");
        inlineKeyboardButton3.setCallbackData("Swaggy");

        List<InlineKeyboardButton> rowList = new ArrayList<>();
        rowList.add(inlineKeyboardButton1);
        rowList.add(inlineKeyboardButton2);
        rowList.add(inlineKeyboardButton3);

        inlineKeyboardMarkup.setKeyboard(Collections.singletonList(rowList));

        SendMessageCallbackHandler sendMessageCallbackHandler = new SendMessageCallbackHandler(chatId, listIdMessage);
        SendMessage message = sendMessageCallbackHandler.forwardMessage("Ciao " + user + "! Seleziona cosa di cui hai bisogno.", callbackQuery);

        message.setReplyMarkup(inlineKeyboardMarkup);

        //listMessage.add(update.getMessage().getMessageId());

        return message;
    }
}
