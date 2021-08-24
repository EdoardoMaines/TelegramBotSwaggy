package handlers.inlineHandler;

import handlers.messageHandler.SendMessageCallbackHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static core.TelegramBot.indirizzoWallet_SWAGGY;

public class InlineAddressWalletHandler extends InlineHandler{
    public InlineAddressWalletHandler(long chatId, String user, List<Integer> listIdMessage) {
        super(chatId, user, listIdMessage);
    }

    public SendMessage sendInlineAddress (CallbackQuery callbackQuery) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText("<-- Back");
        inlineKeyboardButton1.setCallbackData("Back2Lv");

        List<InlineKeyboardButton> rowList1 = new ArrayList<>();

        rowList1.add(inlineKeyboardButton1);

        inlineKeyboardMarkup.setKeyboard(Collections.singletonList(rowList1));

        SendMessageCallbackHandler sendMessageCallbackHandler = new SendMessageCallbackHandler(chatId, listIdMessage);
        SendMessage message = sendMessageCallbackHandler.forwardMessage("Questo è il tuo indirizzo wallet: '" + indirizzoWallet_SWAGGY + "'", callbackQuery);

        message.setReplyMarkup(inlineKeyboardMarkup);

        return message;
    }
}
