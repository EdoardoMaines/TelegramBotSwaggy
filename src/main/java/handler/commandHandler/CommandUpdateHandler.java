//package handler.commandHandler;
//
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Update;
//
//import java.util.List;
//
//public class CommandUpdateHandler extends CommandHandler{
//
//    public CommandUpdateHandler(String command, List<Integer> listIDMessage) {
//        super(command, listIDMessage);
//    }
//
//    public List<SendMessage> executeCommand(Update update) {
//        switch (command) {
////            case "/start":
////
////                SendMessageUpdateHandler sendMessageUpdateHandler = new SendMessageUpdateHandler(update.getMessage().getChatId(), listIDMessage);
////                listIDMessage.add(update.getMessage().getMessageId());
////                access();
////                List<JSONObject> jsonObject = userRestClient.retriveAtheByJson();
////                    for (JSONObject j : jsonObject) {
////                        System.out.print(j.toString());
////                    }
////                    JSONObject jsonObject = userRestClient.retriveAtheByJson();
////                    System.out.print(jsonObject);
////                try {
////                    sendPhoto_update(update.getMessage().getChatId(), "src/main/imgs/swag.jpg", "", update);
////                } catch (TelegramApiValidationException e) {
////                    e.printStackTrace();
////                }
////                try {
////                    SendMessage message1 = sendMessageUpdateHandler.forwardMessage("Ciao " + user + "!\n" +
////                            "Per accedere ai tuoi dati abbiamo bisogno della tua email e della tua password di Swaggy." + "\n" +
////                            "Invia un messaggio contenente SOLO la tua email e la tua password separate da '/'" + "\n" +
////                            "Esempio:", update);
////                    SendMessage message2 = sendMessageUpdateHandler.forwardMessage("'email'/'password'", update);
////                    listMessage.add(message1);
////                    listMessage.add(message2);
////                } catch (TelegramApiException e) {
////                    e.printStackTrace();
////                }
//        }
//            return listMessage;
//    }
//}
