package handlers.inlineHandler;

import handlers.messageHandler.SendMessageCallbackHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineSwaggyHandler extends InlineHandler{
    public InlineSwaggyHandler(long chatId, String user, List<Integer> listIdMessage) {
        super(chatId, user, listIdMessage);
    }

    public SendMessage sendInlineSwaggy (CallbackQuery callbackQuery){

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton5 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText("Indirizzo Wallet");
        inlineKeyboardButton1.setCallbackData("Indirizzo Wallet");
        inlineKeyboardButton2.setText("Saldi Wallet");
        inlineKeyboardButton2.setCallbackData("Saldi Wallet");
        inlineKeyboardButton3.setText("Saldi Account");
        inlineKeyboardButton3.setCallbackData("Saldi Account");
        inlineKeyboardButton4.setText("Sentyment");
        inlineKeyboardButton4.setCallbackData("Sentyment");
        inlineKeyboardButton5.setText("<-- Back");
        inlineKeyboardButton5.setCallbackData("Back1Lv");

        List<InlineKeyboardButton> rowList1 = new ArrayList<>();
        List<InlineKeyboardButton> rowList2 = new ArrayList<>();
        List<InlineKeyboardButton> rowList3 = new ArrayList<>();
        rowList1.add(inlineKeyboardButton1);
        rowList1.add(inlineKeyboardButton2);

        rowList2.add(inlineKeyboardButton3);
        rowList2.add(inlineKeyboardButton4);

        rowList3.add(inlineKeyboardButton5);

        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        list.add(rowList1);
        list.add(rowList2);
        list.add(rowList3);

        inlineKeyboardMarkup.setKeyboard(list);

        SendMessageCallbackHandler sendMessageCallbackHandler = new SendMessageCallbackHandler(chatId, listIdMessage);
        SendMessage message = sendMessageCallbackHandler.forwardMessage("Ciao @" + user + "! Di cosa hai bisogno?", callbackQuery);

        message.setReplyMarkup(inlineKeyboardMarkup);

        return message;
    }
}
