package handlers.inlineHandler;

import handlers.messageHandler.SendMessageCallbackHandler;
import handlers.messageHandler.SendMessageUpdateHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineHandler {

    long chatId;
    String user;
    List<Integer> listIdMessage;

    public InlineHandler(long chatId, String user, List<Integer> listIdMessage) {
        this.chatId = chatId;
        this.user = user;
        this.listIdMessage = listIdMessage;
    }

    public SendMessage sendInline (Update update){

        String user = update.getMessage().getFrom().getUserName();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText("Info valore BTC");
        inlineKeyboardButton1.setCallbackData("Info BTC");
        inlineKeyboardButton2.setText("Macchine Noleggiate");
        inlineKeyboardButton2.setCallbackData("Macchine Noleggiate");
        inlineKeyboardButton3.setText("Swaggy");
        inlineKeyboardButton3.setCallbackData("Swaggy");

        List<InlineKeyboardButton> rowList1 = new ArrayList<>();
        List<InlineKeyboardButton> rowList2 = new ArrayList<>();
        rowList1.add(inlineKeyboardButton1);
        rowList1.add(inlineKeyboardButton3);
        rowList2.add(inlineKeyboardButton2);

        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        list.add(rowList1);
        list.add(rowList2);

        inlineKeyboardMarkup.setKeyboard(list);

        SendMessageUpdateHandler sendMessageUpdateHandler = new SendMessageUpdateHandler(chatId, listIdMessage);
        SendMessage message = sendMessageUpdateHandler.forwardMessage("Ciao @" + user + "! Di cosa hai bisogno?.", update);

        message.setReplyMarkup(inlineKeyboardMarkup);

        return message;
    }
    public SendMessage sendBackInline (CallbackQuery callbackQuery){

        String user = callbackQuery.getFrom().getUserName();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText("Info BTC");
        inlineKeyboardButton1.setCallbackData("Info BTC");
        inlineKeyboardButton2.setText("Macchine Noleggiate");
        inlineKeyboardButton2.setCallbackData("Macchine Noleggiate");
        inlineKeyboardButton3.setText("Swaggy");
        inlineKeyboardButton3.setCallbackData("Swaggy");

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
        SendMessage message = sendMessageCallbackHandler.forwardMessage("Ciao @" + user + "! Di cosa hai bisogno?", callbackQuery);

        message.setReplyMarkup(inlineKeyboardMarkup);

        return message;
    }
}
