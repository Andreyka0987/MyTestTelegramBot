package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class TelegramBot extends TelegramLongPollingBot {
    String key = "";

    public HashMap<Long,User> users = new HashMap<>();




    @Override
    public String getBotUsername() {
        return "MegaSuperDuper202520242022_bot"; // Замініть на ім'я вашого бота
    }

    @Override
    public String getBotToken() {
        return key; // Замініть на токен вашого бота
    }




    @Override
    public void onRegister() {
        super.onRegister();
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String userMessage = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (users.get(chatId) == null) {
                users.put(chatId, new User(update.getMessage().getFrom().getUserName(), chatId));
            }
            if (userMessage.equals("/showhistory")){
            }
            else {
                users.get(chatId).addWord(true, userMessage);
            }



            getInfoFunction(chatId, userMessage, users);
          //  RockScissorsPaperGame(chatId, userMessage, users);
            helpFunction(chatId, userMessage);
            historyFunction(chatId,userMessage,users);
            NumberGuesser(chatId,userMessage, users);
         //   send(chatId,"");



        }else {
            System.out.println("Received non-text message");
        }
    }


    public void getInfoFunction(long chatId, String userMessage, HashMap<Long, User> users) {
        User user = users.get(chatId);

        if (userMessage.equals("/showinfo")){
            send(chatId,"Your info:\n"+"Age: "+user.getAge()+"\n"+"Birthday: "+user.getBirthDay());
        }

        if (userMessage.equals("/stopinfo")){
            user.setInfoStage(0);
            send(chatId, "Info setting stopped");
        }

        if (userMessage.equals("/setinfo")){
            user.setInfoStage(1);
            send(chatId,"Put your age below");
            return;
        }


        if (user.getInfoStage() == 1){
            try {
                int age = Integer.parseInt(userMessage);
                user.setAge(age);
                user.setInfoStage(2);
                send(chatId, " Put your birthday (e.g. 11.11.2011):");
            } catch (NumberFormatException e) {
                send(chatId, "Something gone wrong, try again!");
            }
            return;
        }

        if (user.getInfoStage() == 2){
            user.setBirthDay(userMessage);
            user.setInfoStage(0);
        }


      //  send(chatId, user.getAge()+"\n"+user.getBirthDay());

    }

    boolean isRSPGameStart = false;
    public void RockScissorsPaperGame(long chatId, String userMessage, HashMap<Long, User> users) {
        User user = users.get(chatId);
        String rock = "rock";
        String scissors = "scissors";
        String paper = "paper";

       if (userMessage.equals("/startRSPgame")){
           send(chatId, "Your chose:"+ rock+", "+scissors+", "+paper ); //в процесі розробки
       }




    }


    public void helpFunction(long chatId, String userMessage) {
        if (userMessage.equals("/commandinfo")){
            send(chatId,"All useable commands:\n/deletehistory\n/showhistory\n/startnumberguesser\n/stopnumberguesser\n/newnumber\n/commandinfo");
        }
    }


    public void historyFunction(long chatId, String userMessage, HashMap<Long, User> users) {
                User user = users.get(chatId);
                List<Msg> history = user.getWords();

                if (userMessage.equals("/deletehistory")){
                    user.getWords().removeAll(history);
                    send(chatId, "HistoryDeleted");
                }

                if (userMessage.equals("/showhistory")) {
                    if (history.isEmpty()) {
                        send(chatId, "HistoryEmpty");
                    }
                     else {
                        StringBuilder historyLego = new StringBuilder("YourMessageHistory:\n");
                        for (Msg msg : history) {
                            historyLego.append(msg.getWord()).append("\n");
                        }
                        send(chatId, historyLego.toString());
                    }
                }
            }


    boolean isGameStart = false;
    public void NumberGuesser(long chatId, String userMessage, HashMap<Long, User> users) {
        User user = users.get(chatId);

        if (userMessage.equals("/startnumberguesser")){
            user.setPuzzleNumber(-1);
            send(chatId, "Game Started");
            isGameStart = true;
        }

        if (userMessage.equals("/stopnumberguesser")){
            user.setPuzzleNumber(-2);
            send(chatId, "Game stopped");
            isGameStart = false;
        }

        if (userMessage.equals("/newnumber") && isGameStart){
        //    System.out.println("123");
            users.get(chatId).setPuzzleNumber(-1);
        }
        else if (userMessage.equals("/newnumber") && !isGameStart){
            send(chatId, "You need to start the game");
        }

        int num = Integer.valueOf(userMessage);

     if (user.getPuzzleNumber() == -1) {
         Random random = new Random();
         user.setPuzzleNumber(random.nextInt(100));
     }

        if (user.getPuzzleNumber() != -2){
         if (num > user.getPuzzleNumber()) {
             send(chatId, "take away");}
        if (num < user.getPuzzleNumber()) {
            send(chatId, "add");}
        if (num==user.getPuzzleNumber()) {
            send(chatId, "win");
            user.setPuzzleNumber(-1);
            send(chatId, "New number is ready");
        }
        }
    }


    private void send(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

}



class User{
    private String name;
    private long chatId;
    private List<Msg> words = new ArrayList<>();
    private int puzzleNumber = -2;
    private String RSPchose = "-";
    private int age = 0;
    private String birthDay = "-";
    private int infoStage = 0;

    public User(String name, long chatId) {
        this.name = name;
        this.chatId = chatId;
    }
    public void addWord(boolean b, String word) {
        words.add(new Msg(word, b));
    }
    public List<Msg> getWords() {
        return words;
    }
    public void setPuzzleNumber(int puzzleNumber) {
        this.puzzleNumber = puzzleNumber;
    }
    public int getPuzzleNumber() {
        return puzzleNumber;
    }

    public String getRSPchose() {
        return RSPchose;
    }

    public void setRSPchose(String RSPchose) {
        this.RSPchose = RSPchose;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }
    public int getInfoStage() {
        return infoStage;
    }

    public void setInfoStage(int infoStage) {
        this.infoStage = infoStage;
    }
}

class Msg {
    private final String word;
    private final boolean isTrue;

    public Msg(String word, boolean isTrue) {
        this.word = word;
        this.isTrue = isTrue;
    }
    public String getWord() {
        return word;
    }
    public boolean isTrue() {
        return isTrue;
    }
}












