package handler.inlineHandler;

import handler.messageHandler.SendMessageCallbackHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InlineWalletHandler extends InlineHandler{


    public InlineWalletHandler(long chatId, String user, List<Integer> listMessage) {
        super(chatId, user, listMessage);
    }

    public SendMessage sendInlineWallet (CallbackQuery callbackQuery) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText("SWAGGY");
        inlineKeyboardButton1.setCallbackData("SWAGGY_WALLET");
        inlineKeyboardButton2.setText("SWAGGY_SAVING");
        inlineKeyboardButton2.setCallbackData("SWAGGY_SAVING");
        inlineKeyboardButton3.setText("BETCHA");
        inlineKeyboardButton3.setCallbackData("BETCHA");


        List<InlineKeyboardButton> rowList = new ArrayList<>();
        rowList.add(inlineKeyboardButton1);
        rowList.add(inlineKeyboardButton2);
        rowList.add(inlineKeyboardButton3);

        inlineKeyboardMarkup.setKeyboard(Collections.singletonList(rowList));

        SendMessageCallbackHandler sendMessageCallbackHandler = new SendMessageCallbackHandler(chatId, listMessage);
        SendMessage message = sendMessageCallbackHandler.forwardMessage("Ciao " + user + "! Seleziona cosa di cui hai bisogno.", callbackQuery);

//        SendMessage message = new SendMessage();
//        message.setText("Ciao " + user + "! Seleziona cosa di cui hai bisogno.");
//        message.setChatId(String.valueOf(chatId));
        message.setReplyMarkup(inlineKeyboardMarkup);

        //li.add(callbackQuery.getMessage().getMessageId());

        return message;
    }
}
