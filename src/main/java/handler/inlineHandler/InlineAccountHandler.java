package handler.inlineHandler;

import handler.messageHandler.SendMessageCallbackHandler;
import handler.messageHandler.SendMessageUpdateHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InlineAccountHandler extends InlineHandler {

    public InlineAccountHandler(long chatId, String user, List<Integer> listMessage) {
        super(chatId, user, listMessage);
    }

    public SendMessage sendInlineAccount (CallbackQuery callbackQuery) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton5 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton6 = new InlineKeyboardButton();

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

        List<InlineKeyboardButton> rowList = new ArrayList<>();
        rowList.add(inlineKeyboardButton1);
        rowList.add(inlineKeyboardButton2);
        rowList.add(inlineKeyboardButton3);
        rowList.add(inlineKeyboardButton4);
        rowList.add(inlineKeyboardButton5);
        rowList.add(inlineKeyboardButton6);

        inlineKeyboardMarkup.setKeyboard(Collections.singletonList(rowList));

        SendMessageCallbackHandler sendMessageCallbackHandler = new SendMessageCallbackHandler(chatId, listMessage);
        SendMessage message = sendMessageCallbackHandler.forwardMessage("Ciao " + user + "! Seleziona cosa di cui hai bisogno.", callbackQuery);

//        SendMessage message = new SendMessage();
//        message.setText("Ciao " + this.user + "! Seleziona cosa di cui hai bisogno.");
//        message.setChatId(String.valueOf(chatId));
        message.setReplyMarkup(inlineKeyboardMarkup);

        //listMessage.add(callbackQuery.getMessage().getMessageId());

        return message;
    }
}
