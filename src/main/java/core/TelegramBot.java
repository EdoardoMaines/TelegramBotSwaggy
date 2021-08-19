package core;

import entities.*;
import lombok.NoArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import entities.User;
import reactor.core.publisher.Mono;
import restClients.*;
import org.springframework.web.reactive.function.client.*;

import java.io.*;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static constants.UrlConstant.*;

@NoArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    public WebClient userWebClient = WebClient.create(User_ModuleBaseURL);
    public WebClient rentWebClient = WebClient.create(Machine_ModuleBaseURL);
    public WebClient currencyWebClient = WebClient.create(Currency_ModuleBaseURL);
    public WebClient sentymentWebClient = WebClient.create(Sentyment_ModuleBaseURL);
    public WebClient accountWebClient = WebClient.create(Account_ModuleBaseURL);
    public WebClient walletWebClient = WebClient.create(Wallet_ModuleBaseURL);


    public UserRestClient userRestClient = new UserRestClient(userWebClient);
    public RentRestClient rentRestClient = new RentRestClient(rentWebClient);
    public CurrencyRestClient currencyRestClient = new CurrencyRestClient(currencyWebClient);
    public SentymentRestClient sentymentRestClient = new SentymentRestClient(sentymentWebClient);
    public AccountRestClient accountRestClient = new AccountRestClient(accountWebClient);
    public WalletRestClient walletRestClient = new WalletRestClient(walletWebClient);


    List<Rent> listRent = new ArrayList<>();

    static List<Integer> listIdMessage = new ArrayList<>();
    static List<Integer> listIdPhoto = new ArrayList<>();


    public long chatId;
    public int idStopCommand;
    public static String user;
    public static String userId;
    public static String email;
    public static String password;
    public static String passwordCritto;
    public static String sentyment_STATUS;
    public static String indirizzoWallet_SWAGGY;


    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static Boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
            return matcher.find();
    }
    public static Boolean checkCommands(String text) {
        if (text.equals("/start") || text.equals("/stop")){
            return false;
        }
        return true;
    }
    public static Boolean checkInput(String text) {
        if (text.contains("/")) {
            String input [] = text.split("/");
            if (validate(input[0])){
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    public static Boolean checkPassword(String psw, User user) {
        if (psw.equals(user.getPassword())) {
            return true;
        }
        return false;
    }


    @Override
    public void onClosing() {
        exe.shutdown();
    }
    

    @Override
    public String getBotUsername() {
        return "myBot";
    }

    @Override
    public String getBotToken() {
        return "1942630650:AAEaQJiEhArTLGCwi7AEkzJqoBsyVGXltjk";
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                user = update.getMessage().getFrom().getUserName();
                chatId = update.getMessage().getChatId();

                if (update.getMessage().getText().equals("/start")){
                    listIdMessage.add(update.getMessage().getMessageId());
                    //access();
                    //List<JSONObject> jsonObject = userRestClient.retriveAtheByJson();
//                    for (JSONObject j : jsonObject) {
//                        System.out.print(j.toString());
//                    }
                    JSONObject jsonObject = userRestClient.retriveAtheByJson();
                    System.out.print(jsonObject);
                    try {
                        sendPhoto_update(update.getMessage().getChatId(), "src/main/imgs/swag.jpg", "", update);
                    } catch (TelegramApiValidationException e) {
                        e.printStackTrace();
                    }
                    sendMessage_update(chatId,"Ciao " + user + "!\n" +
                            "Per accedere ai tuoi dati abbiamo bisogno della tua email e della tua password di Swaggy." + "\n" +
                            "Invia un messaggio contenente SOLO la tua email e la tua password separate da '/'" + "\n" +
                            "Esempio:", update);
                    sendMessage_update(chatId,"'email'/'password'", update);
                }
                if (checkCommands(update.getMessage().getText()) && checkInput(update.getMessage().getText())) {
                    String input [] = update.getMessage().getText().split("/");

                    listIdMessage.add(update.getMessage().getMessageId());
                    email = input[0];
                    password = input[1];

                    try {
                        User user = userRestClient.retriveUserByEmail(email);
                        userId = user.getId();
                        listRent = rentRestClient.retriveRentByUserId(userId);
                        List<Wallet> listWallet = walletRestClient.retriveWalletById(userId, "SWAGGY");
                        indirizzoWallet_SWAGGY = listWallet.get(0).getAddress();

                        if (checkCommands(update.getMessage().getText()) && checkPassword(password, user)) {
                            sendMessage_update(chatId, "Ciao! L'email (" + user.getEmail() + ") e la password (" + user.getPassword() + ") che hai inserito sono corrette", update);

                            try {
                                System.out.print(passwordCritto);
                                execute(sendInlineGlobal(chatId, update));
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        } else {
                            sendMessage_update(chatId, "Ops! La password che hai inserito è scorretta.", update);
                        }
                    } catch (WebClientResponseException e) {
                        e.printStackTrace();
                        sendMessage_update(chatId, "L'email che hai inserito non è corretta!", update);
                    }
                }
                if (checkCommands(update.getMessage().getText()) && !checkInput(update.getMessage().getText())) {
                    listIdMessage.add(update.getMessage().getMessageId());
                    sendMessage_update(chatId, "Ops! Il messaggio che hai inviato o la password non sono corretti", update);
                    //deleteMessage(2000, update);
                }
                if (update.getMessage().getText().equals("/stop")){
                    //listIdMessage.add(update.getMessage().getMessageId());

                    userWebClient.delete();
                    rentWebClient.delete();

                    deleteAllChat(update);
                    sendLastMessage(chatId,"Ciao, alla prossima!", update);

                    deleteLastMessage(2000);
                }
            }
        }
        if (update.hasCallbackQuery()) {
            if (update.getCallbackQuery().getData() != null) {

                switch (update.getCallbackQuery().getData()) {
                    case "Info BTC":
                        try {
                            execute(sendInlineBTC(chatId, update.getCallbackQuery()));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "Macchine Noleggiate":
                        if(!listRent.isEmpty()) {
                            for (Rent r : listRent) {
                                sendMessage_callbackQuery(chatId, "Queste sono le tue macchine noleggiate!", update.getCallbackQuery());
                                sendMessage_callbackQuery(chatId, r.toString(), update.getCallbackQuery());
                            }
                        } else {
                            sendMessage_callbackQuery(chatId, "Non hai macchine noleggiate!", update.getCallbackQuery());
                        }
                        break;
                    case "Swaggy":
                        try {
                            sendPhoto_callbackQuery(chatId, "src/main/imgs/swaggy.JPG", "", update.getCallbackQuery());
                            execute(sendInlineSwaggy(chatId, update.getCallbackQuery()));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "Indirizzo Wallet":
                        sendMessage_callbackQuery(chatId, "Questo è il tuo indirizzo wallet: '" + indirizzoWallet_SWAGGY + "'", update.getCallbackQuery());
                        break;
                    case "Saldi Wallet":
                        try {
                            execute(sendInlineWallet(chatId, update.getCallbackQuery()));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "Saldi Account":
                        try {
                            execute(sendInlineAccount(chatId, update.getCallbackQuery()));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "Sentyment":
//                        Sentyment sentyment = sentymentRestClient.retriveSentymentById(userId);
//                        sentyment_STATUS = sentyment.getStatus();
//                        if(sentyment_STATUS.equals("NEGATIVO")) {
                            try {
                                sendPhoto_callbackQuery(chatId, "src/main/imgs/sentyment.png", "", update.getCallbackQuery());
                                sendInlineSentyment(chatId, update.getCallbackQuery());
                            } catch (TelegramApiValidationException | NullPointerException e) {
                                e.printStackTrace();
                            }
//                        } else {
//                            sendMessage_callbackQuery(chatId, "Il servizio Sentyment è ATTIVO", update.getCallbackQuery());
//                        }
                        break;
                    case "EUR":
                        Currency currency_EUR = currencyRestClient.retriveCurrencyBySymbol("BTC", "EUR");
                        sendMessage_callbackQuery(chatId, "Valore del BTC: " + currency_EUR.getRate() + " €", update.getCallbackQuery());
                        break;
                    case "USD":
                        Currency currency_USD = currencyRestClient.retriveCurrencyBySymbol("BTC", "USD");
                        sendMessage_callbackQuery(chatId, "Valore del BTC: " + currency_USD.getRate() + " $", update.getCallbackQuery());
                        break;
                    case "OMNIBUS":
                        List<Account> account_OMNIBUS = accountRestClient.retriveAccountById(userId, "OMNIBUS");
                        if (!account_OMNIBUS.isEmpty()) {
                            sendMessage_callbackQuery(chatId, "Saldo OMNIBUS: " + account_OMNIBUS.get(0).getBalance() + " €", update.getCallbackQuery());
                        } else {
                            sendMessage_callbackQuery(chatId, "Non hai un account BALANCE!", update.getCallbackQuery());
                        }
                        break;
                    case "STANDARD":
                        List<Account> account_STANDARD = accountRestClient.retriveAccountById(userId, "STANDARD");
                        if (!account_STANDARD.isEmpty()) {
                            sendMessage_callbackQuery(chatId, "Saldo STANDARD: " + account_STANDARD.get(0).getBalance() + " €", update.getCallbackQuery());
                        } else {
                            sendMessage_callbackQuery(chatId, "Non hai un account STANDARD!", update.getCallbackQuery());
                        }
                        break;
                    case "SAVING":
                        List<Account> account_SAVING = accountRestClient.retriveAccountById(userId, "SAVING");
                        if (!account_SAVING.isEmpty()) {
                            sendMessage_callbackQuery(chatId, "Saldo SAVING: " + account_SAVING.get(0).getBalance() + " €", update.getCallbackQuery());
                        } else {
                            sendMessage_callbackQuery(chatId, "Non hai un account SAVING!", update.getCallbackQuery());
                        }
                        break;
                    case "SWAGGY_CARD":
                        List<Account> account_SWAGGY_CARD = accountRestClient.retriveAccountById(userId, "SWAGGY_CARD");
                        if (!account_SWAGGY_CARD.isEmpty()) {
                            sendMessage_callbackQuery(chatId, "Saldo SWAGGY_CARD: " + account_SWAGGY_CARD.get(0).getBalance() + " €", update.getCallbackQuery());
                        } else {
                            sendMessage_callbackQuery(chatId, "Non hai un account SWAGGY_CARD!", update.getCallbackQuery());
                        }
                        break;
                    case "EMONEY_CARD":
                        List<Account> account_EMONEY_CARD = accountRestClient.retriveAccountById(userId, "EMONEY_CARD");
                        if (!account_EMONEY_CARD.isEmpty()) {
                            sendMessage_callbackQuery(chatId, "Saldo EMONEY_CARD: " + account_EMONEY_CARD.get(0).getBalance() + " €", update.getCallbackQuery());
                        } else {
                            sendMessage_callbackQuery(chatId, "Non hai un account EMONEY_CARD!", update.getCallbackQuery());
                        }
                        break;
                    case "EMONEY_CARD_VIP":
                        List<Account> account_EMONEY_CARD_VIP = accountRestClient.retriveAccountById(userId, "EMONEY_CARD_VIP");
                        if (!account_EMONEY_CARD_VIP.isEmpty()) {
                            sendMessage_callbackQuery(chatId, "Saldo EMONEY_CARD_VIP: " + account_EMONEY_CARD_VIP.get(0).getBalance() + " €", update.getCallbackQuery());
                        } else {
                            sendMessage_callbackQuery(chatId, "Non hai un account EMONEY_CARD_VIP!", update.getCallbackQuery());
                        }
                        break;
                    case "SWAGGY_WALLET":
                        List<Wallet> wallet_SWAGGY = walletRestClient.retriveWalletById(userId, "SWAGGY");
                        if (!wallet_SWAGGY.isEmpty()) {
                            sendMessage_callbackQuery(chatId, "Saldo wallet SWAGGY: " + wallet_SWAGGY.get(0).getBalance() + " BTC", update.getCallbackQuery());
                        } else {
                            sendMessage_callbackQuery(chatId, "Non hai un wallet SWAGGY!", update.getCallbackQuery());
                        }
                        break;
                    case "SWAGGY_SAVING":
                        List<Wallet> wallet_SAVING = walletRestClient.retriveWalletById(userId, "SWAGGY_SAVING");
                        if (!wallet_SAVING.isEmpty()) {
                            sendMessage_callbackQuery(chatId, "Saldo wallet SWAGGY_SAVING: " + wallet_SAVING.get(0).getBalance() + " BTC", update.getCallbackQuery());
                        } else {
                            sendMessage_callbackQuery(chatId, "Non hai un wallet SWAGGY_SAVING!", update.getCallbackQuery());
                        }
                        break;
                    case "BETCHA":
                        List<Wallet> wallet_BETCHA = walletRestClient.retriveWalletById(userId, "BETCHA");
                        if (!wallet_BETCHA.isEmpty()) {
                            sendMessage_callbackQuery(chatId, "Saldo wallet BETCHA: " + wallet_BETCHA.get(0).getBalance() + "BTC", update.getCallbackQuery());
                        } else {
                            sendMessage_callbackQuery(chatId, "Non hai un wallet BETCHA!", update.getCallbackQuery());
                        }
                        break;
                }
            }
        } else if (!update.hasCallbackQuery()) {
            if (update.hasMessage()) {
                System.out.println(update.getMessage().getText());

            } else {
                System.out.println(update.getMyChatMember().getOldChatMember().getStatus());
                System.out.println(update.getMyChatMember().getChat().getId());
                System.out.println("NESSUN MESSAGGIO!");
            }
        }
    }
//    public void answerCallback(CallbackQuery callBack) throws TelegramApiException {
//        CallbackQuery callbackQuery = callBack;
//        String callbackQueryId = callbackQuery != null ? callbackQuery.getId() : "invalid_query_id";
//        AnswerCallbackQuery answer = new AnswerCallbackQuery(callbackQueryId);
//        answer.setText("Caricamento...");
//        answer.setShowAlert(false);
//        answer.setCacheTime(1);
//        execute(answer);
//    }
    public static SendMessage sendInlineGlobal (long chatId, Update update){

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

        List<InlineKeyboardButton> rowList = new ArrayList<>();
        rowList.add(inlineKeyboardButton1);
        rowList.add(inlineKeyboardButton2);
        rowList.add(inlineKeyboardButton3);

        inlineKeyboardMarkup.setKeyboard(Collections.singletonList(rowList));

        SendMessage message = new SendMessage();
        message.setText("Ciao " + user + "! Seleziona cosa di cui hai bisogno.");
        message.setChatId(String.valueOf(chatId));
        message.setReplyMarkup(inlineKeyboardMarkup);

        listIdMessage.add(update.getMessage().getMessageId());

        return message;
    }
    public static SendMessage sendInlineAccount (long chatId, CallbackQuery callbackQuery) {

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

        SendMessage message = new SendMessage();
        message.setText("Ciao " + user + "! Seleziona cosa di cui hai bisogno.");
        message.setChatId(String.valueOf(chatId));
        message.setReplyMarkup(inlineKeyboardMarkup);

        listIdMessage.add(callbackQuery.getMessage().getMessageId());

        return message;
    }
    public static SendMessage sendInlineWallet (long chatId, CallbackQuery callbackQuery) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText("SWAGGY");
        inlineKeyboardButton1.setCallbackData("SWAGGY_WALLET");
        inlineKeyboardButton2.setText("SWAGGY_SAVING");
        inlineKeyboardButton2.setCallbackData("SWAGGY_SAVING");
        inlineKeyboardButton3.setText("BETCHA");
        inlineKeyboardButton3.setCallbackData("BETCHA");


        List<InlineKeyboardButton> rowList = new ArrayList<>();
        rowList.add(inlineKeyboardButton1);
        rowList.add(inlineKeyboardButton2);
        rowList.add(inlineKeyboardButton3);

        inlineKeyboardMarkup.setKeyboard(Collections.singletonList(rowList));

        SendMessage message = new SendMessage();
        message.setText("Ciao " + user + "! Seleziona cosa di cui hai bisogno.");
        message.setChatId(String.valueOf(chatId));
        message.setReplyMarkup(inlineKeyboardMarkup);

        listIdMessage.add(callbackQuery.getMessage().getMessageId());

        return message;
    }
    public static SendMessage sendInlineBTC (long chatId, CallbackQuery callbackQuery){

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

        SendMessage message = new SendMessage();
        message.setText("Valore in tempo reale del BTC nelle seguenti valute:");
        message.setChatId(String.valueOf(chatId));
        message.setReplyMarkup(inlineKeyboardMarkup);

        listIdMessage.add(callbackQuery.getMessage().getMessageId());

        return message;
    }
    public static SendMessage sendInlineSwaggy (long chatId, CallbackQuery callbackQuery){

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

        SendMessage message = new SendMessage();
        message.setText("Tramite questi bottoni protrai visualizzare le informazioni riguardanti:");
        message.setChatId(String.valueOf(chatId));
        message.setReplyMarkup(inlineKeyboardMarkup);

        listIdMessage.add(callbackQuery.getMessage().getMessageId());

        return message;
    }
    public void sendInlineSentyment (long chatId, CallbackQuery callbackQuery){

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText("Vai al link");
        inlineKeyboardButton1.setUrl("https://my.swaggyapp.com/login");
        List<InlineKeyboardButton> rowList = new ArrayList<>();

        rowList.add(inlineKeyboardButton1);
        inlineKeyboardMarkup.setKeyboard(Collections.singletonList(rowList));

        SendMessage message = new SendMessage();
        message.setText("Non sai quando acquistare o vendere BTC? Abbiamo noi la soluzione adatta per te, il nostro servizio 'SENTIMENT'!\n \n" +
                "Accedi a 'Swaggy App' con le tue credenziali e troverai ciò di cui hai bisogno!");

        message.setChatId(String.valueOf(chatId));
        message.setReplyMarkup(inlineKeyboardMarkup);

        listIdMessage.add(callbackQuery.getMessage().getMessageId());

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void sendMessage_update (Long chatId, String text, Update update){

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        listIdMessage.add(update.getMessage().getMessageId());

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void sendMessage_callbackQuery (Long chatId, String text, CallbackQuery callbackQuery){

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        listIdMessage.add(callbackQuery.getMessage().getMessageId());

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private int sendLastMessage (Long chatId, String text, Update update){

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return update.getMessage().getMessageId();
    }
    private void deleteAllChat(Update update) {
        idStopCommand = update.getMessage().getMessageId();

        for(int i=idStopCommand; i>=idStopCommand - (listIdMessage.size()+listIdPhoto.size()); i--) {
            DeleteMessage deleteMessage = new DeleteMessage(String.valueOf(chatId), i);

            try {
                execute(deleteMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        listIdMessage.clear();
        listIdPhoto.clear();
    }
    private void deleteLastMessage(int delay) {
        DeleteMessage deleteMessage = new DeleteMessage(String.valueOf(chatId), idStopCommand+1);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void sendPhoto_update(Long chatId, String pathPhoto, String captionPhoto, Update update) throws TelegramApiValidationException {

        SendPhoto sendMessage = new SendPhoto();
        sendMessage.setCaption(captionPhoto);
        sendMessage.setPhoto(new InputFile(new File(pathPhoto)));
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.validate();

        listIdPhoto.add(update.getMessage().getMessageId());

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void sendPhoto_callbackQuery(Long chatId, String pathPhoto, String captionPhoto, CallbackQuery callbackQuery) throws TelegramApiValidationException {

        SendPhoto sendMessage = new SendPhoto();
        sendMessage.setCaption(captionPhoto);
        sendMessage.setPhoto(new InputFile(new File(pathPhoto)));
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.validate();

        listIdPhoto.add(callbackQuery.getMessage().getMessageId());

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void sendGif (Long chatId, String pathGif, String captionGif, CallbackQuery callbackQuery) throws TelegramApiValidationException {

        SendAnimation sendAnimation = new SendAnimation();
        sendAnimation.setCaption(captionGif);
        sendAnimation.setAnimation(new InputFile(new File(pathGif)));
        sendAnimation.setChatId(String.valueOf(chatId));
        sendAnimation.validate();

        listIdPhoto.add(callbackQuery.getMessage().getMessageId());

        try {
            execute(sendAnimation);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void access() {
        try {
            URL url = new URL("https://api.swaggyapp.com/user/v1/user/public-login");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            int status = con.getResponseCode();
            System.out.println(status);
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.getInputStream();
            con.getOutputStream();
            String jsonInputString = "{\"username\": \"mainesedoardo@gmail.com\",\n" +
                    "  \"password\": \"Tirocinio99Swag!\",\n" +
                    "  \"skipOtpCheck\": true}";

//            try (OutputStream os = con.getOutputStream()) {
//                byte[] input = jsonInputString.getBytes("utf-8");
//                os.write(input, 0, input.length);
//            }
//
//            try (BufferedReader br = new BufferedReader(
//                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
//                StringBuilder response = new StringBuilder();
//                String responseLine = null;
//                while ((responseLine = br.readLine()) != null) {
//                    response.append(responseLine.trim());
//                }
//                System.out.println(response);
//            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        String command = "curl -X 'POST'" +
//                "  'http://localhost:8444/public/login'" +
//                "  -H 'accept: */*'" +
//                "  -H 'Content-Type: application/json'" +
//                "  -d '{" +
//                "  \"username\": \"mainesedoardo@gmail.com\"," +
//                "  \"password\": \"Tirocinio99Swag!\"," +
//                "  \"skipOtpCheck\": true" +
//                "}'";
//        try {
//            Process process = Runtime.getRuntime().exec(command);
//            process.getInputStream();
//
//            System.out.print(process.);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}