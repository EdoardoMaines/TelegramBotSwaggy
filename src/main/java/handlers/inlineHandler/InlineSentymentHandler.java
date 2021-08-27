package handlers.inlineHandler;

import handlers.messageHandler.SendMessageCallbackHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineSentymentHandler extends  InlineHandler{
    public InlineSentymentHandler(long chatId, String user, List<Integer> listIdMessage) {
        super(chatId, user, listIdMessage);
    }

    public SendMessage sendInlineSentyment (CallbackQuery callbackQuery){

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();

        inlineKeyboardButton2.setText("<-- Back");
        inlineKeyboardButton2.setCallbackData("Back2Lv");

        inlineKeyboardButton1.setText("Vai al link");
        inlineKeyboardButton1.setUrl("https://my.swaggyapp.com/login");
        List<InlineKeyboardButton> rowList1 = new ArrayList<>();
        List<InlineKeyboardButton> rowList2 = new ArrayList<>();

        rowList1.add(inlineKeyboardButton1);
        rowList2.add(inlineKeyboardButton2);

        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        list.add(rowList1);
        list.add(rowList2);

        inlineKeyboardMarkup.setKeyboard(list);

        SendMessageCallbackHandler sendMessageCallbackHandler = new SendMessageCallbackHandler(chatId, listIdMessage);
        SendMessage message = sendMessageCallbackHandler.forwardMessage("Non sai quando acquistare o vendere BTC? Abbiamo noi la soluzione adatta per te, il nostro servizio 'SENTIMENT'!\n \n" +
                "Accedi a 'Swaggy App' con le tue credenziali e troverai ciò di cui hai bisogno!", callbackQuery);

        message.setReplyMarkup(inlineKeyboardMarkup);

        return message;
    }
}
