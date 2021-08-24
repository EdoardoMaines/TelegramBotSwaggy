//package models;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//@Data
//public class Inline {
//    int nr_buttons;
//    List<InlineKeyboardButton> listButtons;
//
//    public Inline(int nr_buttons) {
//        this.nr_buttons = nr_buttons;
//    }
//
//    public List<InlineKeyboardButton> populate() {
//        for (int b = 0; b <nr_buttons; b++) {
//            InlineKeyboardButton inlineKeyboardButton + b = new InlineKeyboardButton();
//        }
//        return listButtons;
//    }
//    InlineKeyboardMarkup inlineKeyboardMarkup + i = new InlineKeyboardMarkup();
//
//    InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
//    InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
//
//        inlineKeyboardButton1.setText("Info BTC");
//        inlineKeyboardButton1.setCallbackData("Info BTC");
//        inlineKeyboardButton2.setText("Macchine Noleggiate");
//        inlineKeyboardButton2.setCallbackData("Macchine Noleggiate");
//        inlineKeyboardButton3.setText("Swaggy");
//        inlineKeyboardButton3.setCallbackData("Swaggy");
//
//    List<InlineKeyboardButton> rowList = new ArrayList<>();
//        rowList.add(inlineKeyboardButton1);
//        rowList.add(inlineKeyboardButton2);
//        rowList.add(inlineKeyboardButton3);
//
//        inlineKeyboardMarkup.setKeyboard(Collections.singletonList(rowList));
//}
