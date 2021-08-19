package core;

import entities.*;
import handler.commandHandler.CommandHandler;
import handler.deleteMessageHandler.DeleteAllMessagesHandler;
import handler.deleteMessageHandler.DeleteLastMessageHandler;
import handler.inlineHandler.*;
import handler.messageHandler.SendMessageCallbackHandler;
import handler.messageHandler.SendMessageUpdateHandler;
import lombok.NoArgsConstructor;
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

    public static List<Integer> listIdMessage = new ArrayList<>();
    public static List<Integer> listIdPhoto = new ArrayList<>();


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
                SendMessageUpdateHandler sendMessageUpdateHandler = new SendMessageUpdateHandler(chatId, listIdMessage);

                if (update.getMessage().getText().equals("/start")){
                    listIdMessage.add(update.getMessage().getMessageId());
                    //access();
                    //List<JSONObject> jsonObject = userRestClient.retriveAtheByJson();
//                    for (JSONObject j : jsonObject) {
//                        System.out.print(j.toString());
//                    }
//                    JSONObject jsonObject = userRestClient.retriveAtheByJson();
//                    System.out.print(jsonObject);
                    try {
                        sendPhoto_update(update.getMessage().getChatId(), "src/main/imgs/swag.jpg", "", update);
                    } catch (TelegramApiValidationException e) {
                        e.printStackTrace();
                    }
                    try {
                        execute(sendMessageUpdateHandler.forwardMessage("Ciao " + user + "!\n" +
                                "Per accedere ai tuoi dati abbiamo bisogno della tua email e della tua password di Swaggy." + "\n" +
                                "Invia un messaggio contenente SOLO la tua email e la tua password separate da '/'" + "\n" +
                                "Esempio:", update));
                        execute(sendMessageUpdateHandler.forwardMessage("'email'/'password'", update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
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
                            try {
                                System.out.print(passwordCritto);
                                InlineHandler inlineHandler = new InlineHandler(chatId, user.toString(), listIdMessage);
                                execute(inlineHandler.sendInline(update));
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                execute(sendMessageUpdateHandler.forwardMessage("Ops! La password che hai inserito è scorretta.", update));
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (WebClientResponseException e) {
                        e.printStackTrace();
                        try {
                            execute(sendMessageUpdateHandler.forwardMessage("L'email che hai inserito non è corretta!", update));
                        } catch (TelegramApiException telegramApiException) {
                            telegramApiException.printStackTrace();
                        }
                    }
                }
                if (checkCommands(update.getMessage().getText()) && !checkInput(update.getMessage().getText())) {
                    listIdMessage.add(update.getMessage().getMessageId());
                    try {
                        execute(sendMessageUpdateHandler.forwardMessage("Ops! Il messaggio che hai inviato o la password non sono corretti", update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                if (update.getMessage().getText().equals("/stop")){
                    //listIdMessage.add(update.getMessage().getMessageId());

                    userWebClient.delete();
                    rentWebClient.delete();

                    DeleteAllMessagesHandler deleteAllMessagesHandler = new DeleteAllMessagesHandler(chatId, update.getMessage().getMessageId(), listIdMessage, listIdPhoto);
                    for (DeleteMessage message : deleteAllMessagesHandler.deleteAllChat()) {
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                    //deleteAllChat(update);
                    try {
                        execute(sendMessageUpdateHandler.forwardMessage("Ciao, alla prossima!", update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    //sendLastMessage(chatId,"Ciao, alla prossima!", update);
                    //deleteLastMessage(2000);
                    DeleteLastMessageHandler deleteLastMessageHandler = new DeleteLastMessageHandler(chatId, update.getMessage().getMessageId(), listIdMessage, listIdPhoto);
                    try {
                        execute(deleteLastMessageHandler.deleteLastMessage(2000));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (update.hasCallbackQuery()) {
            if (update.getCallbackQuery().getData() != null) {
                CommandHandler commandHandler = new CommandHandler(update.getCallbackQuery().getData(),listIdMessage);
                for(SendMessage message : commandHandler.executeCommand(update.getCallbackQuery(), listRent, userId)) {
                    try {
                        execute(message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
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