package handler.commandHandler;

import entities.Account;
import entities.Currency;
import entities.Rent;
import entities.Wallet;
import handler.inlineHandler.*;
import handler.messageHandler.SendMessageCallbackHandler;
import handler.messageHandler.SendMessageUpdateHandler;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
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

    public CommandHandler(String command, List<Integer> listIdMessage) {
        this.command = command;
        this.listIdMessage = listIdMessage;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public List<SendMessage> executeCommand(CallbackQuery callbackQuery, List<Rent> listRent, String userId) {

        long chatId = callbackQuery.getMessage().getChatId();
        String user = callbackQuery.getFrom().getUserName();

        WebClient currencyWebClient = WebClient.create(Currency_ModuleBaseURL);
        WebClient accountWebClient = WebClient.create(Account_ModuleBaseURL);
        WebClient walletWebClient = WebClient.create(Wallet_ModuleBaseURL);

        CurrencyRestClient currencyRestClient = new CurrencyRestClient(currencyWebClient);
        AccountRestClient accountRestClient = new AccountRestClient(accountWebClient);
        WalletRestClient walletRestClient = new WalletRestClient(walletWebClient);

        List<SendMessage> listMessage = new ArrayList<>();
        SendMessageCallbackHandler sendMessageCallbackHandler = new SendMessageCallbackHandler(chatId, listIdMessage);

        switch (command) {
            case "Info BTC":
                InlineBTCHandler inlineBTCHandler = new InlineBTCHandler(chatId, user, listIdMessage);
                SendMessage message1 = inlineBTCHandler.sendInlineBTC(callbackQuery);
                listMessage.add(message1);
                return listMessage;
            case "Macchine Noleggiate":
                if(!listRent.isEmpty()) {
                    for (Rent r : listRent) {
                        SendMessage message2 = sendMessageCallbackHandler.forwardMessage("Queste sono le tue macchine noleggiate!", callbackQuery);
                        SendMessage message3 = sendMessageCallbackHandler.forwardMessage(r.toString(), callbackQuery);
                        listMessage.add(message2);
                        listMessage.add(message3);
                        return listMessage;
                    }
                } else {
                    SendMessage message4 = sendMessageCallbackHandler.forwardMessage("Non hai macchine noleggiate!", callbackQuery);
                    listMessage.add(message4);
                    return listMessage;
                }
            case "Swaggy":
                //sendPhoto_callbackQuery(chatId, "src/main/imgs/swaggy.JPG", "", update.getCallbackQuery());
                InlineSwaggyHandler inlineSwaggyHandler = new InlineSwaggyHandler(chatId, user, listIdMessage);
                SendMessage message5 = inlineSwaggyHandler.sendInlineSwaggy(callbackQuery);
                listMessage.add(message5);
                return listMessage;
            case "Indirizzo Wallet":
                SendMessage message6 = sendMessageCallbackHandler.forwardMessage("Questo è il tuo indirizzo wallet: '" + indirizzoWallet_SWAGGY + "'", callbackQuery);
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
//                Sentyment sentyment = sentymentRestClient.retriveSentymentById(userId);
//                sentyment_STATUS = sentyment.getStatus();
//                if(sentyment_STATUS.equals("NEGATIVO")) {
                //sendPhoto_callbackQuery(chatId, "src/main/imgs/sentyment.png", "", update.getCallbackQuery());
                InlineSentymentHandler inlineSentymentHandler = new InlineSentymentHandler(chatId, user, listIdMessage);
                //sendInlineSentyment(chatId, update.getCallbackQuery());
                SendMessage message9 = inlineSentymentHandler.sendInlineSentyment(callbackQuery);
//                } else {
//                    sendMessage_callbackQuery(chatId, "Il servizio Sentyment è ATTIVO", update.getCallbackQuery());
//                }
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
        }
        return null;
    }
}
