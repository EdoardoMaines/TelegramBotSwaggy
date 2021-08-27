package core;

import entities.*;
import handlers.commandHandler.CommandHandler;
import handlers.deleteMessageHandler.DeleteAllMessagesHandler;
import handlers.deleteMessageHandler.DeleteLastMessageHandler;
import handlers.inlineHandler.*;
import handlers.messageHandler.SendLastMessageHandler;
import handlers.messageHandler.SendMessageUpdateHandler;
import handlers.messageHandler.SendPhotoUpdateHandler;
import lombok.NoArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.*;
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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static constants.UrlConstant.*;

@NoArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {


    public WebClient userWebClient = WebClient.create(User_ModuleBaseURL);
    public WebClient rentWebClient = WebClient.create(Machine_ModuleBaseURL);
    public WebClient walletWebClient = WebClient.create(Wallet_ModuleBaseURL);


    public UserRestClient userRestClient = new UserRestClient(userWebClient);
    public RentRestClient rentRestClient = new RentRestClient(rentWebClient);
    public WalletRestClient walletRestClient = new WalletRestClient(walletWebClient);


    List<Rent> listRent = new ArrayList<>();

    public static List<Integer> listIdMessage = new ArrayList<>();
    public static List<Integer> listIdPhoto = new ArrayList<>();


    public long chatId;
    public static String user;
    public static String userId;
    public static String email;
    public static String password;
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
    public String getBotUsername() {
        return "SwaggyBot";
    }
    @Override
    public String getBotToken() {
        return "1901387562:AAGC5a8NWpnZdjZoGIGkzmURWOSFtxQ1M84";
    }
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                user = update.getMessage().getFrom().getUserName();
                chatId = update.getMessage().getChatId();
                SendMessageUpdateHandler sendMessageUpdateHandler = new SendMessageUpdateHandler(chatId, listIdMessage);
                SendPhotoUpdateHandler sendPhotoUpdateHandler = new SendPhotoUpdateHandler(update.getMessage().getChatId(), listIdPhoto);

                if (update.getMessage().getText().equals("/start")){
                    listIdMessage.add(update.getMessage().getMessageId());
                    try {
                        execute(sendPhotoUpdateHandler.forwardPhoto( "src/main/imgs/swag.jpg", "", update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    try {
                        execute(sendMessageUpdateHandler.forwardMessage("Ciao @" + user + ", Benvenuto! Io sono 'SwaggyBot'.\n\n" +
                                "Ho bisogno della tua email e della tua password di Swaggy." + "\n" +
                                "Inviami un messaggio contenente SOLO la tua email e la tua password separate da '/'" + "\n\n" +
                                "Ti faccio vedere un esempio di come devi fare:", update));
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
                                InlineHandler inlineHandler = new InlineHandler(chatId, user.toString(), listIdMessage);
                                execute(inlineHandler.sendInline(update));
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                execute(sendMessageUpdateHandler.forwardMessage("Ops! La password che hai inserito non è corretta.", update));
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (WebClientResponseException e) {
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
                        execute(sendMessageUpdateHandler.forwardMessage("Ops! Il messaggio che hai inviato non è nella forma corretta", update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                if (update.getMessage().getText().equals("/stop")){
                    userWebClient.delete();
                    rentWebClient.delete();

                    DeleteAllMessagesHandler deleteAllMessagesHandler = new DeleteAllMessagesHandler(chatId, update.getMessage().getMessageId(), sendMessageUpdateHandler.getListIdMessage(), sendPhotoUpdateHandler.getListIdPhoto());
                    for (DeleteMessage message : deleteAllMessagesHandler.deleteAllChat()) {
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                    SendLastMessageHandler sendLastMessageHandler = new SendLastMessageHandler(chatId);

                    DeleteLastMessageHandler deleteLastMessageHandler = new DeleteLastMessageHandler(chatId, update.getMessage().getMessageId(), listIdMessage, listIdPhoto);
                    try {
                        execute(sendLastMessageHandler.forwardLastMessage("Ciao, alla prossima!"));
                        execute(deleteLastMessageHandler.deleteLastMessage(2000));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (update.hasCallbackQuery()) {
            if (update.getCallbackQuery().getData() != null) {
                CommandHandler commandHandler = new CommandHandler(update.getCallbackQuery().getData(),listIdMessage, listIdPhoto, listRent);
                for(Object message : commandHandler.executeCommand(update.getCallbackQuery(), userId)) {
                    try {
                        if (message instanceof SendMessage) {
                            execute((SendMessage) message);
                        }
                        else {
                            execute((SendPhoto) message);
                        }
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
}