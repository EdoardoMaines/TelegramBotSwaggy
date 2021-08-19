package handler.inlineHandler;

import handler.messageHandler.SendMessageCallbackHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InlineSwaggyHandler extends InlineHandler{
    public InlineSwaggyHandler(long chatId, String user, List<Integer> listMessage) {
        super(chatId, user, listMessage);
    }

    public SendMessage sendInlineSwaggy (CallbackQuery callbackQuery){

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText("Indirizzo Wallet");
        inlineKeyboardButton1.setCallbackData("Indirizzo Wallet");
        inlineKeyboardButton2.setText("Saldi Wallet");
        inlineKeyboardButton2.setCallbackData("Saldi Wallet");
        inlineKeyboardButton3.setText("Saldi Account");
        inlineKeyboardButton3.setCallbackData("Saldi Account");
        inlineKeyboardButton4.setText("Sentyment");
        inlineKeyboardButton4.setCallbackData("Sentyment");

        List<InlineKeyboardButton> rowList = new ArrayList<>();
        rowList.add(inlineKeyboardButton1);
        rowList.add(inlineKeyboardButton2);
        rowList.add(inlineKeyboardButton3);
        rowList.add(inlineKeyboardButton4);

        inlineKeyboardMarkup.setKeyboard(Collections.singletonList(rowList));

        SendMessageCallbackHandler sendMessageCallbackHandler = new SendMessageCallbackHandler(chatId, listMessage);
        SendMessage message = sendMessageCallbackHandler.forwardMessage("Tramite questi bottoni protrai visualizzare le informazioni riguardanti:", callbackQuery);

//        SendMessage message = new SendMessage();
//        message.setText("Tramite questi bottoni protrai visualizzare le informazioni riguardanti:");
//        message.setChatId(String.valueOf(chatId));
        message.setReplyMarkup(inlineKeyboardMarkup);

        //listIdMessage.add(callbackQuery.getMessage().getMessageId());

        return message;
    }
}
