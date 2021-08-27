package handlers.commandHandler;

import entities.Account;
import entities.Currency;
import entities.Rent;
import entities.Wallet;
import handlers.inlineHandler.*;
import handlers.messageHandler.SendMessageCallbackHandler;
import handlers.messageHandler.SendPhotoCallbackHandler;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import restClients.AccountRestClient;
import restClients.CurrencyRestClient;
import restClients.WalletRestClient;

import java.util.ArrayList;
import java.util.List;

import static constants.UrlConstant.*;
import static core.TelegramBot.indirizzoWallet_SWAGGY;

public class CommandHandler {
    String command;
    List<Integer> listIdMessage;
    List<Integer> listIdPhoto;
    List<Rent> listRent;

    public CommandHandler(String command, List<Integer> listIdMessage, List<Integer> listIdPhoto, List<Rent> listRent) {
        this.command = command;
        this.listIdMessage = listIdMessage;
        this.listIdPhoto = listIdPhoto;
        this.listRent = listRent;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public ArrayList executeCommand(CallbackQuery callbackQuery, String userId) {

        long chatId = callbackQuery.getMessage().getChatId();
        String user = callbackQuery.getFrom().getUserName();

        WebClient currencyWebClient = WebClient.create(Currency_ModuleBaseURL);
        WebClient accountWebClient = WebClient.create(Account_ModuleBaseURL);
        WebClient walletWebClient = WebClient.create(Wallet_ModuleBaseURL);

        CurrencyRestClient currencyRestClient = new CurrencyRestClient(currencyWebClient);
        AccountRestClient accountRestClient = new AccountRestClient(accountWebClient);
        WalletRestClient walletRestClient = new WalletRestClient(walletWebClient);


        ArrayList listMessage = new ArrayList<>();

        SendMessageCallbackHandler sendMessageCallbackHandler = new SendMessageCallbackHandler(chatId, listIdMessage);

        switch (command) {
            case "Info BTC":
                InlineBTCHandler inlineBTCHandler = new InlineBTCHandler(chatId, user, listIdMessage);
                SendMessage message1 = inlineBTCHandler.sendInlineBTC(callbackQuery);
                listMessage.add(message1);
                return listMessage;
            case "Macchine Noleggiate":
                InlineRentHandler inlineRentHandler = new InlineRentHandler(chatId, user, listIdMessage, listRent);
                List<SendMessage> list = inlineRentHandler.sendInlineRent(callbackQuery);
                listMessage.addAll(list);
                return listMessage;
            case "Swaggy":
                SendPhotoCallbackHandler sendPhotoCallbackHandler1 = new SendPhotoCallbackHandler(chatId, listIdPhoto);
                SendPhoto photo1 = sendPhotoCallbackHandler1.forwardPhoto("src/main/imgs/swaggy.JPG", "", callbackQuery);
                InlineSwaggyHandler inlineSwaggyHandler = new InlineSwaggyHandler(chatId, user, listIdMessage);
                SendMessage message5 = inlineSwaggyHandler.sendInlineSwaggy(callbackQuery);
                listMessage.add(photo1);
                listMessage.add(message5);
                return listMessage;
            case "Indirizzo Wallet":
                InlineAddressWalletHandler inlineAddressWalletHandler = new InlineAddressWalletHandler(chatId, user, listIdMessage);
                SendMessage message6 = inlineAddressWalletHandler.sendInlineAddress(callbackQuery);
                listMessage.add(message6);
                return listMessage;
            case "Saldi Wallet":
                InlineWalletHandler inlineWalletHandler = new InlineWalletHandler(chatId, user, listIdMessage);
                SendMessage message7 = inlineWalletHandler.sendInlineWallet(callbackQuery);
                listMessage.add(message7);
                return listMessage;
            case "Saldi Account":
                InlineAccountHandler inlineAccountHandler = new InlineAccountHandler(chatId, user, listIdMessage);
                SendMessage message8 = inlineAccountHandler.sendInlineAccount(callbackQuery);
                listMessage.add(message8);
                return listMessage;
            case "Sentyment":
                SendPhotoCallbackHandler sendPhotoCallbackHandler2 = new SendPhotoCallbackHandler(chatId, listIdPhoto);
                SendPhoto photo2 = sendPhotoCallbackHandler2.forwardPhoto("src/main/imgs/sentyment.png", "", callbackQuery);
                InlineSentymentHandler inlineSentymentHandler = new InlineSentymentHandler(chatId, user, listIdMessage);
                SendMessage message9 = inlineSentymentHandler.sendInlineSentyment(callbackQuery);
                listMessage.add(photo2);
                listMessage.add(message9);
                return listMessage;
            case "EUR":
                Currency currency_EUR = currencyRestClient.retriveCurrencyBySymbol("BTC", "EUR");
                SendMessage message10 = sendMessageCallbackHandler.forwardMessage("Valore del BTC: " + currency_EUR.getRate() + " €", callbackQuery);
                listMessage.add(message10);
                return listMessage;
            case "USD":
                Currency currency_USD = currencyRestClient.retriveCurrencyBySymbol("BTC", "USD");
                SendMessage message11 = sendMessageCallbackHandler.forwardMessage("Valore del BTC: " + currency_USD.getRate() + " $", callbackQuery);
                listMessage.add(message11);
                return listMessage;
            case "OMNIBUS":
                List<Account> account_OMNIBUS = accountRestClient.retriveAccountById(userId, "OMNIBUS");
                if (!account_OMNIBUS.isEmpty()) {
                    SendMessage message12 = sendMessageCallbackHandler.forwardMessage("Saldo OMNIBUS: " + account_OMNIBUS.get(0).getBalance() + " €", callbackQuery);
                    listMessage.add(message12);
                } else {
                    SendMessage message13 = sendMessageCallbackHandler.forwardMessage("Non hai un account BALANCE!", callbackQuery);
                    listMessage.add(message13);
                }
                return listMessage;
            case "STANDARD":
                List<Account> account_STANDARD = accountRestClient.retriveAccountById(userId, "STANDARD");
                if (!account_STANDARD.isEmpty()) {
                    SendMessage message13 = sendMessageCallbackHandler.forwardMessage("Saldo STANDARD: " + account_STANDARD.get(0).getBalance() + " €", callbackQuery);
                    listMessage.add(message13);
                } else {
                    SendMessage message14 = sendMessageCallbackHandler.forwardMessage("Non hai un account STANDARD!", callbackQuery);
                    listMessage.add(message14);
                }
                return listMessage;
            case "SAVING":
                List<Account> account_SAVING = accountRestClient.retriveAccountById(userId, "SAVING");
                if (!account_SAVING.isEmpty()) {
                    SendMessage message15 = sendMessageCallbackHandler.forwardMessage("Saldo SAVING: " + account_SAVING.get(0).getBalance() + " €", callbackQuery);
                    listMessage.add(message15);
                } else {
                    SendMessage message16 = sendMessageCallbackHandler.forwardMessage("Non hai un account SAVING!", callbackQuery);
                    listMessage.add(message16);
                }
                return listMessage;
            case "SWAGGY_CARD":
                List<Account> account_SWAGGY_CARD = accountRestClient.retriveAccountById(userId, "SWAGGY_CARD");
                if (!account_SWAGGY_CARD.isEmpty()) {
                    SendMessage message17 = sendMessageCallbackHandler.forwardMessage("Saldo SWAGGY_CARD: " + account_SWAGGY_CARD.get(0).getBalance() + " €",callbackQuery);
                    listMessage.add(message17);
                } else {
                    SendMessage message18 = sendMessageCallbackHandler.forwardMessage("Non hai un account SWAGGY_CARD!", callbackQuery);
                    listMessage.add(message18);
                }
                return listMessage;
            case "EMONEY_CARD":
                List<Account> account_EMONEY_CARD = accountRestClient.retriveAccountById(userId, "EMONEY_CARD");
                if (!account_EMONEY_CARD.isEmpty()) {
                    SendMessage message19 = sendMessageCallbackHandler.forwardMessage("Saldo EMONEY_CARD: " + account_EMONEY_CARD.get(0).getBalance() + " €",callbackQuery);
                    listMessage.add(message19);
                } else {
                    SendMessage message20 = sendMessageCallbackHandler.forwardMessage("Non hai un account EMONEY_CARD!", callbackQuery);
                    listMessage.add(message20);
                }
                return listMessage;
            case "EMONEY_CARD_VIP":
                List<Account> account_EMONEY_CARD_VIP = accountRestClient.retriveAccountById(userId, "EMONEY_CARD_VIP");
                if (!account_EMONEY_CARD_VIP.isEmpty()) {
                    SendMessage message21 = sendMessageCallbackHandler.forwardMessage("Saldo EMONEY_CARD_VIP: " + account_EMONEY_CARD_VIP.get(0).getBalance() + " €",callbackQuery);
                    listMessage.add(message21);
                } else {
                    SendMessage message22 = sendMessageCallbackHandler.forwardMessage("Non hai un account EMONEY_CARD_VIP!", callbackQuery);
                    listMessage.add(message22);
                }
                return listMessage;
            case "SWAGGY_WALLET":
                List<Wallet> wallet_SWAGGY = walletRestClient.retriveWalletById(userId, "SWAGGY");
                if (!wallet_SWAGGY.isEmpty()) {
                    SendMessage message23 = sendMessageCallbackHandler.forwardMessage("Saldo wallet SWAGGY: " + wallet_SWAGGY.get(0).getBalance() + " BTC", callbackQuery);
                    listMessage.add(message23);
                } else {
                    SendMessage message24 = sendMessageCallbackHandler.forwardMessage("Non hai un wallet SWAGGY!", callbackQuery);
                    listMessage.add(message24);
                }
                return listMessage;
            case "SWAGGY_SAVING":
                List<Wallet> wallet_SAVING = walletRestClient.retriveWalletById(userId, "SWAGGY_SAVING");
                if (!wallet_SAVING.isEmpty()) {
                    SendMessage message25 = sendMessageCallbackHandler.forwardMessage("Saldo wallet SWAGGY_SAVING: " + wallet_SAVING.get(0).getBalance() + " BTC",callbackQuery);
                    listMessage.add(message25);
                } else {
                    SendMessage message26 = sendMessageCallbackHandler.forwardMessage("Non hai un wallet SWAGGY_SAVING!", callbackQuery);
                    listMessage.add(message26);
                }
                return listMessage;
            case "BETCHA":
                List<Wallet> wallet_BETCHA = walletRestClient.retriveWalletById(userId, "BETCHA");
                if (!wallet_BETCHA.isEmpty()) {
                    SendMessage message27 = sendMessageCallbackHandler.forwardMessage("Saldo wallet BETCHA: " + wallet_BETCHA.get(0).getBalance() + "BTC",callbackQuery);
                    listMessage.add(message27);
                } else {
                    SendMessage message28 = sendMessageCallbackHandler.forwardMessage("Non hai un wallet BETCHA!", callbackQuery);
                    listMessage.add(message28);
                }
                return listMessage;
            case "Back1Lv":
                InlineHandler inlineHandler = new InlineHandler(chatId, user, listIdMessage);
                SendMessage message29 = inlineHandler.sendBackInline(callbackQuery);
                SendPhotoCallbackHandler sendPhotoCallbackHandler3 = new SendPhotoCallbackHandler(chatId, listIdPhoto);
                SendPhoto photo3 = sendPhotoCallbackHandler3.forwardPhoto("src/main/imgs/swag.jpg", "", callbackQuery);
                listMessage.add(photo3);
                listMessage.add(message29);
                return listMessage;
            case "Back2Lv":
                InlineSwaggyHandler inlineSwaggyHandler1 = new InlineSwaggyHandler(chatId, user, listIdMessage);
                SendMessage message30 = inlineSwaggyHandler1.sendInlineSwaggy(callbackQuery);
                SendPhotoCallbackHandler sendPhotoCallbackHandler4 = new SendPhotoCallbackHandler(chatId, listIdPhoto);
                SendPhoto photo4 = sendPhotoCallbackHandler4.forwardPhoto("src/main/imgs/swaggy.JPG", "", callbackQuery);
                listMessage.add(photo4);
                listMessage.add(message30);
                return listMessage;
        }
        return null;
    }
}
