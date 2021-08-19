package handler.inlineHandler;

import handler.messageHandler.SendMessageCallbackHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InlineSentymentHandler extends  InlineHandler{
    public InlineSentymentHandler(long chatId, String user, List<Integer> listMessage) {
        super(chatId, user, listMessage);
    }

    public SendMessage sendInlineSentyment (CallbackQuery callbackQuery){

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText("Vai al link");
        inlineKeyboardButton1.setUrl("https://my.swaggyapp.com/login");
        List<InlineKeyboardButton> rowList = new ArrayList<>();

        rowList.add(inlineKeyboardButton1);
        inlineKeyboardMarkup.setKeyboard(Collections.singletonList(rowList));

        SendMessageCallbackHandler sendMessageCallbackHandler = new SendMessageCallbackHandler(chatId, listMessage);
        SendMessage message = sendMessageCallbackHandler.forwardMessage("Non sai quando acquistare o vendere BTC? Abbiamo noi la soluzione adatta per te, il nostro servizio 'SENTIMENT'!\n \n" +
                "Accedi a 'Swaggy App' con le tue credenziali e troverai ciò di cui hai bisogno!", callbackQuery);

//        SendMessage message = new SendMessage();
//        message.setText("Non sai quando acquistare o vendere BTC? Abbiamo noi la soluzione adatta per te, il nostro servizio 'SENTIMENT'!\n \n" +
//                "Accedi a 'Swaggy App' con le tue credenziali e troverai ciò di cui hai bisogno!");
//
//        message.setChatId(String.valueOf(chatId));
        message.setReplyMarkup(inlineKeyboardMarkup);

//        listIdMessage.add(callbackQuery.getMessage().getMessageId());
//
//        try {
//            execute(message);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
        return message;
    }
}
