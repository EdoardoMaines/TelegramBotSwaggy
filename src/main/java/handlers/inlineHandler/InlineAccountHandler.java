package handlers.inlineHandler;

import handlers.messageHandler.SendMessageCallbackHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineAccountHandler extends InlineHandler {

    public InlineAccountHandler(long chatId, String user, List<Integer> listIdMessage) {
        super(chatId, user, listIdMessage);
    }

    public SendMessage sendInlineAccount (CallbackQuery callbackQuery) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton5 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton6 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton7 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText("OMNIBUS");
        inlineKeyboardButton1.setCallbackData("OMNIBUS");
        inlineKeyboardButton2.setText("STANDARD");
        inlineKeyboardButton2.setCallbackData("STANDARD");
        inlineKeyboardButton3.setText("SAVING");
        inlineKeyboardButton3.setCallbackData("SAVING");

        inlineKeyboardButton4.setText("SWAGGY_CARD");
        inlineKeyboardButton4.setCallbackData("SWAGGY_CARD");
        inlineKeyboardButton5.setText("EMONEY_CARD");
        inlineKeyboardButton5.setCallbackData("EMONEY_CARD");
        inlineKeyboardButton6.setText("EMONEY_CARD_VIP");
        inlineKeyboardButton6.setCallbackData("EMONEY_CARD_VIP");

        inlineKeyboardButton7.setText("<-- Back");
        inlineKeyboardButton7.setCallbackData("Back2Lv");

        List<InlineKeyboardButton> rowList1 = new ArrayList<>();
        List<InlineKeyboardButton> rowList2 = new ArrayList<>();
        List<InlineKeyboardButton> rowList3 = new ArrayList<>();

        List<List<InlineKeyboardButton>> list = new ArrayList<>();

        rowList1.add(inlineKeyboardButton1);
        rowList1.add(inlineKeyboardButton2);
        rowList1.add(inlineKeyboardButton3);
        rowList2.add(inlineKeyboardButton4);
        rowList2.add(inlineKeyboardButton5);
        rowList2.add(inlineKeyboardButton6);
        rowList3.add(inlineKeyboardButton7);

        list.add(rowList1);
        list.add(rowList2);
        list.add(rowList3);

        inlineKeyboardMarkup.setKeyboard(list);

        SendMessageCallbackHandler sendMessageCallbackHandler = new SendMessageCallbackHandler(chatId, listIdMessage);
        SendMessage message = sendMessageCallbackHandler.forwardMessage("Ciao @" + user + "! Ecco a te i conti Swaggy disponibili.", callbackQuery);

        message.setReplyMarkup(inlineKeyboardMarkup);

        return message;
    }
}
