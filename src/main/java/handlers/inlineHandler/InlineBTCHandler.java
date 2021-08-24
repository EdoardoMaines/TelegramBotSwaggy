package handlers.inlineHandler;

import handlers.messageHandler.SendMessageCallbackHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InlineBTCHandler extends InlineHandler{
    public InlineBTCHandler(long chatId, String user, List<Integer> listIdMessage) {
        super(chatId, user, listIdMessage);
    }

    public SendMessage sendInlineBTC (CallbackQuery callbackQuery){

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText("Valore[€]");
        inlineKeyboardButton1.setCallbackData("EUR");
        inlineKeyboardButton2.setText("Valore[$]");
        inlineKeyboardButton2.setCallbackData("USD");
        inlineKeyboardButton3.setText("<-- Back");
        inlineKeyboardButton3.setCallbackData("Back1Lv");

        List<InlineKeyboardButton> rowList1 = new ArrayList<>();
        List<InlineKeyboardButton> rowList2 = new ArrayList<>();

        rowList1.add(inlineKeyboardButton1);
        rowList1.add(inlineKeyboardButton2);

        rowList2.add(inlineKeyboardButton3);

        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        list.add(rowList1);
        list.add(rowList2);

        inlineKeyboardMarkup.setKeyboard(list);

        SendMessageCallbackHandler sendMessageCallbackHandler = new SendMessageCallbackHandler(chatId, listIdMessage);
        SendMessage message = sendMessageCallbackHandler.forwardMessage("Valore in tempo reale del BTC nelle seguenti valute:", callbackQuery);

//        SendMessage message = new SendMessage();
//        message.setText("Valore in tempo reale del BTC nelle seguenti valute:");
//        message.setChatId(String.valueOf(chatId));
        message.setReplyMarkup(inlineKeyboardMarkup);

        //listIdMessage.add(callbackQuery.getMessage().getMessageId());

        return message;
    }
}
