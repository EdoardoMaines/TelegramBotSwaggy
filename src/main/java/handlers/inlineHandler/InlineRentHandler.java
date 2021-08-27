package handlers.inlineHandler;

import entities.Rent;
import handlers.messageHandler.SendMessageCallbackHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InlineRentHandler extends InlineHandler{

    List<Rent> listRent;
    public InlineRentHandler(long chatId, String user, List<Integer> listIdMessage, List<Rent> listRent) {
        super(chatId, user, listIdMessage);
        this.listRent = listRent;
    }

    public List<SendMessage> sendInlineRent (CallbackQuery callbackQuery) {

        List<SendMessage> listMessage = new ArrayList<>();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText("<-- Back");
        inlineKeyboardButton1.setCallbackData("Back1Lv");

        List<InlineKeyboardButton> rowList1 = new ArrayList<>();

        rowList1.add(inlineKeyboardButton1);

        inlineKeyboardMarkup.setKeyboard(Collections.singletonList(rowList1));

        SendMessageCallbackHandler sendMessageCallbackHandler = new SendMessageCallbackHandler(chatId, listIdMessage);

        if(!listRent.isEmpty()) {

            for (Rent r : listRent) {
                SendMessage message3 = sendMessageCallbackHandler.forwardMessage(r.toString(), callbackQuery);
                listMessage.add(message3);
            }
            SendMessage message2 = sendMessageCallbackHandler.forwardMessage("Queste sono le tue macchine noleggiate!", callbackQuery);
            message2.setReplyMarkup(inlineKeyboardMarkup);
            listMessage.add(message2);

        } else {
            SendMessage message4 = sendMessageCallbackHandler.forwardMessage("Ops, non hai macchine noleggiate!", callbackQuery);
            message4.setReplyMarkup(inlineKeyboardMarkup);
            listMessage.add(message4);
        }
        return listMessage;
    }
}
