package handlers.inlineHandler;

import handlers.messageHandler.SendMessageCallbackHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InlineWalletHandler extends InlineHandler{


    public InlineWalletHandler(long chatId, String user, List<Integer> listIdMessage) {
        super(chatId, user, listIdMessage);
    }

    public SendMessage sendInlineWallet (CallbackQuery callbackQuery) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText("SWAGGY");
        inlineKeyboardButton1.setCallbackData("SWAGGY_WALLET");
        inlineKeyboardButton2.setText("SWAGGY_SAVING");
        inlineKeyboardButton2.setCallbackData("SWAGGY_SAVING");
        inlineKeyboardButton3.setText("BETCHA");
        inlineKeyboardButton3.setCallbackData("BETCHA");
        inlineKeyboardButton4.setText("<-- Back");
        inlineKeyboardButton4.setCallbackData("Back2Lv");


        List<InlineKeyboardButton> rowList1 = new ArrayList<>();
        List<InlineKeyboardButton> rowList2 = new ArrayList<>();
        rowList1.add(inlineKeyboardButton1);
        rowList1.add(inlineKeyboardButton2);
        rowList1.add(inlineKeyboardButton3);
        rowList2.add(inlineKeyboardButton4);

        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        list.add(rowList1);
        list.add(rowList2);

        inlineKeyboardMarkup.setKeyboard(list);

        SendMessageCallbackHandler sendMessageCallbackHandler = new SendMessageCallbackHandler(chatId, listIdMessage);
        SendMessage message = sendMessageCallbackHandler.forwardMessage("Ciao " + user + "! Seleziona cosa di cui hai bisogno.", callbackQuery);

//        SendMessage message = new SendMessage();
//        message.setText("Ciao " + user + "! Seleziona cosa di cui hai bisogno.");
//        message.setChatId(String.valueOf(chatId));
        message.setReplyMarkup(inlineKeyboardMarkup);

        //li.add(callbackQuery.getMessage().getMessageId());

        return message;
    }
}
