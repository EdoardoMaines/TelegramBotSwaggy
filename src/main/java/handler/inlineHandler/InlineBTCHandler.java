package handler.inlineHandler;

import handler.messageHandler.SendMessageCallbackHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InlineBTCHandler extends InlineHandler{
    public InlineBTCHandler(long chatId, String user, List<Integer> listMessage) {
        super(chatId, user, listMessage);
    }

    public SendMessage sendInlineBTC (CallbackQuery callbackQuery){

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText("Valore[€]");
        inlineKeyboardButton1.setCallbackData("EUR");
        inlineKeyboardButton2.setText("Valore[$]");
        inlineKeyboardButton2.setCallbackData("USD");

        List<InlineKeyboardButton> rowList = new ArrayList<>();
        rowList.add(inlineKeyboardButton1);
        rowList.add(inlineKeyboardButton2);

        inlineKeyboardMarkup.setKeyboard(Collections.singletonList(rowList));

        SendMessageCallbackHandler sendMessageCallbackHandler = new SendMessageCallbackHandler(chatId, listMessage);
        SendMessage message = sendMessageCallbackHandler.forwardMessage("Valore in tempo reale del BTC nelle seguenti valute:", callbackQuery);

//        SendMessage message = new SendMessage();
//        message.setText("Valore in tempo reale del BTC nelle seguenti valute:");
//        message.setChatId(String.valueOf(chatId));
        message.setReplyMarkup(inlineKeyboardMarkup);

        //listIdMessage.add(callbackQuery.getMessage().getMessageId());

        return message;
    }
}
