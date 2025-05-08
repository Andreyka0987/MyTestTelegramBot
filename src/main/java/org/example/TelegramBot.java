package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

public class TelegramBot extends TelegramLongPollingBot {
    String key = "";

    public HashMap<Long, User> users = new HashMap<>();
    int[][] array = {
            {1, 2, 3, 4, 5},
            {6, 7, 8, 9, 10},
            {11, 12, 13, 14, 15},
            {16, 17, 18, 19, 20}
    };


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
            if (userMessage.equals("/showhistory")) {
                System.out.println("123");
            } else {
                users.get(chatId).addWord(true, userMessage);
            }


            sendButtons(chatId);
            //  getInfoFunction(chatId, userMessage, users);
            //  RockScissorsPaperGame(chatId, userMessage, users);
//            helpFunction(chatId, userMessage);
            //           historyFunction(chatId,userMessage,users);
//            NumberGuesser(chatId,userMessage, users);
            //   send(chatId,"");
            RockScissorsPaperGame(chatId, userMessage, users);


        } else if (update.hasCallbackQuery()) {
            boolean a = true;
            String callbackData = update.getCallbackQuery().getData();
            String userMessage = update.getCallbackQuery().getMessage().toString();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            int x = users.get(chatId).getX();
            int y = users.get(chatId).getY();

            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array.length; j++) {
                    if (array[i][j] == DB.getDb() && a) {
                        y = i;
                        x = j;
                        a = false;
                    }
                }
            }

            if (callbackData.equals("up")) {
                if (y > 0) {
                    y--;
                    users.get(chatId).setY(y);
                    send(chatId, "Твоя ячейка:\n" + array[y][x]);
                    DB.SetDb(array[y][x]);
                } else {
                    send(chatId, "Далі не буде");
                }

            } else if (callbackData.equals("down")) {
                if (y < array.length - 1) {
                    y++;
                    users.get(chatId).setY(y);
                    send(chatId, "Твоя ячейка:\n" + array[y][x]);
                    DB.SetDb(array[y][x]);
                } else {
                    send(chatId, "Далі не буде");
                }

            } else if (callbackData.equals("right")) {
                if (x < array[y].length - 1) {
                    x++;
                    users.get(chatId).setX(x);
                    send(chatId, "Твоя ячейка:\n" + array[y][x]);
                    DB.SetDb(array[y][x]);
                } else {
                    send(chatId, "Далі не буде");
                }

            } else if (callbackData.equals("left")) {
                if (x > 0) {
                    x--;
                    users.get(chatId).setX(x);
                    send(chatId, "Твоя ячейка:\n" + array[y][x]);
                    DB.SetDb(array[y][x]);
                }


            }
        } else {
            System.out.println("Received non-text message");
        }
    }

    public void sendButtons(long chatId) {
//        InlineKeyboardButton addInfoButton = new InlineKeyboardButton();
//        addInfoButton.setText("➕ Додати в історію");
//        addInfoButton.setCallbackData("add_info");
//
//        InlineKeyboardButton removeHistoryButton = new InlineKeyboardButton();
//        removeHistoryButton.setText("🗑️ Очистити історію");
//        removeHistoryButton.setCallbackData("remove_history");
//
//        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
//        rows.add(Arrays.asList(addInfoButton));
//        rows.add(Arrays.asList(removeHistoryButton));


//        InlineKeyboardButton addUpButton = new InlineKeyboardButton();
//        addUpButton.setText("↑");
//        addUpButton.setCallbackData("up");
//
//        InlineKeyboardButton addDownButton = new InlineKeyboardButton();
//        addDownButton.setText("↓");
//        addDownButton.setCallbackData("down");
//
//        InlineKeyboardButton addRightButton = new InlineKeyboardButton();
//        addRightButton.setText("→");
//        addRightButton.setCallbackData("right");
//
//        InlineKeyboardButton addLeftButton = new InlineKeyboardButton();
//        addLeftButton.setText("←");
//        addLeftButton.setCallbackData("left");
//
//        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
//        rows.add(Arrays.asList(addUpButton));
//        rows.add(Arrays.asList(addRightButton));
//        rows.add(Arrays.asList(addLeftButton));
//        rows.add(Arrays.asList(addDownButton));
//
//        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
//        markup.setKeyboard(rows);
//
//        SendMessage message = new SendMessage();
//        message.setChatId(chatId);
//        message.setText("Game very very funny");
//        message.setReplyMarkup(markup);
//
//        try {
//            execute(message);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
    }

    public void getInfoFunction(long chatId, String userMessage, HashMap<Long, User> users) {
        User user = users.get(chatId);

        if (userMessage.equals("/showinfo")) {
            send(chatId, "Your info:\n" + "Age: " + user.getAge() + "\n" + "Birthday: " + user.getBirthDay());
        }

        if (userMessage.equals("/stopinfo")) {
            user.setInfoStageForRsp(0);
            send(chatId, "Info setting stopped");
        }

        if (userMessage.equals("/setinfo")) {
            user.setInfoStageForRsp(1);
            send(chatId, "Put your age below");
            return;
        }


        if (user.getInfoStageForRsp() == 1) {
            try {
                int age = Integer.parseInt(userMessage);
                user.setAge(age);
                user.setInfoStageForRsp(2);
                send(chatId, " Put your birthday (e.g. 11.11.2011):");
            } catch (NumberFormatException e) {
                send(chatId, "Something gone wrong, try again!");
            }
            return;
        }

        if (user.getInfoStageForRsp() == 2) {
            user.setBirthDay(userMessage);
            user.setInfoStageForRsp(0);
        }


        //  send(chatId, user.getAge()+"\n"+user.getBirthDay());

    }

    String a;
    String b;
    ArrayList<User> userArrayList = new ArrayList<>();
    int newRandom = 0;
    HashMap<Integer, String> RSPMap = new HashMap<>();

    public void RockScissorsPaperGame(long chatId, String userMessage, HashMap<Long, User> users) {
        User user = users.get(chatId);

//        String rock = "rock";
//        String scissors = "scissors";
//        String paper = "paper";
//
//        RSPMap.put(1,rock);
//        RSPMap.put(2,scissors);
//        RSPMap.put(3,paper);

//
//        if (newRandom == -1) {
//            Random random = new Random();
//            user.setRSPChose(random.nextInt(3)+1);
//        }

        if (userMessage.equals("/startRSPgame")) {
            userArrayList.add(user);
            send(chatId, "Wait for game to start");
            user.setInfoStageForRsp(0);
        }

        User user1 = null;
        User user2 = null;
        if (userArrayList.size() % 2 == 0 && user.getInfoStageForRsp() == 0) {
            user1 = userArrayList.get(userArrayList.size() - 2);
            user2 = userArrayList.get(userArrayList.size() - 1);

            if (user1 != user2) {
                user1.setInfoStageForRsp(1);
                user2.setInfoStageForRsp(1);

                ;
                send(user1.getChatId(), "Your playing against: " + user2.getName() + "\nChose: rock paper or scissors");
                send(user2.getChatId(), "Your playing against: " + user1.getName() + "\nChose: rock paper or scissors");
            }

        else {
            send(user1.getChatId(), "You can't play with your self");
            userArrayList.remove(user1);
            userArrayList.remove(user2);}

        }

        if (user.getInfoStageForRsp() == 1) {
            String choice = userMessage.toLowerCase();
            if (choice.equals("rock") || choice.equals("paper") || choice.equals("scissors")) {
                user.setInfoStageForRspSecond(choice);
                user.setInfoStageForRsp(2);
                send(user.getChatId(), "You chose: " + choice);
            } else {
                send(user.getChatId(), "Invalid choice. Type: rock, paper or scissors");
            }
        }


        if (userArrayList.size() >= 2) {
            for (int i = 0; i < userArrayList.size() - 1; i += 2) {
                 user1 = userArrayList.get(i);
                 user2 = userArrayList.get(i + 1);

                if (user1.getInfoStageForRsp() == 2 && user2.getInfoStageForRsp() == 2) {
                    String choice1 = user1.getInfoStageForRspSecond();
                    String choice2 = user2.getInfoStageForRspSecond();

                    String result1;
                    String result2;

                    if (choice1.equals(choice2)) {
                        result1 = "Draw";
                        result2 = "Draw";
                    } else if (
                            (choice1.equals("rock") && choice2.equals("scissors")) ||
                                    (choice1.equals("scissors") && choice2.equals("paper")) ||
                                    (choice1.equals("paper") && choice2.equals("rock"))
                    ) {
                        result1 = "You win!";
                        result2 = "You lose!";
                    } else {
                        result1 = "You lose!";
                        result2 = "You win!";
                    }

                    if (user.getInfoStageForRsp() == 2) {
                        send(user1.getChatId(), "You chose: " + choice1 + "\nOpponent chose: " + choice2 + "\n" + result1 + "\n" +
                                "if you want to continued write /restartrspgame if not /stoprspgame");
                        send(user2.getChatId(), "You chose: " + choice2 + "\nOpponent chose: " + choice1 + "\n" + result2 + "\n" +
                                "if you want to continued write /restartrspgame if not /stoprspgame");
                        user.setInfoStageForRsp(3);
                    }



                if (user.getInfoStageForRsp() == 3 && userMessage.equals("/stoprspgame")) {
                    user1.setInfoStageForRsp(-1);
                    user2.setInfoStageForRsp(-1);
                    user1.setInfoStageForRspSecond("");
                    user2.setInfoStageForRspSecond("");
                    send(user.getChatId(), "Game stopped");

                    userArrayList.remove(user1);
                    userArrayList.remove(user2);
                    break;
                }
                    if (user.getInfoStageForRsp() == 3 && userMessage.equals("/restartrspgame")){
                        user.setInfoStageForRsp(0);
                        send(user.getChatId(),"Wait for game to start");
                    }
                }
            }
        }
    }






    public int getUserChoice(User user){
        return Integer.parseInt(String.valueOf(user.getWords().getLast()));
    }


    public void helpFunction(long chatId, String userMessage) {
        if (userMessage.equals("/commandinfo")){
            send(chatId,"All useable commands:\n/deletehistory\n/showhistory\n/startnumberguesser\n/stopnumberguesser\n/newnumber" +
                    "\n/startRSPgame\n/commandinfo");

        }
    }


    public void deleteHistoryFunction(long chatId, String userMessage, HashMap<Long, User> users) {
                User user = users.get(chatId);
                List<Msg> history = user.getWords();
                for (Msg msg : history){
                    user.getWords().remove(msg);
                }
                send(chatId, "History cleared");
    }

    public void historyFunction(long chatId, String userMessage, HashMap<Long, User> users) {

                User user = users.get(chatId);
                List<Msg> history = user.getWords();

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
    private int infoStageForRsp = 0;
    private String infoStageForRspSecond;
    private int x = 0;
    private int y = 1;
    private int RSPChose = 0;

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
    public int getInfoStageForRsp() {
        return infoStageForRsp;
    }

    public void setInfoStageForRsp(int infoStageForRsp) {
        this.infoStageForRsp = infoStageForRsp;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRSPChose() {
        return RSPChose;
    }

    public void setRSPChose(int RSPChose) {
        this.RSPChose = RSPChose;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getInfoStageForRspSecond() {
        return infoStageForRspSecond;
    }

    public void setInfoStageForRspSecond(String infoStageForRspSecond) {
        this.infoStageForRspSecond = infoStageForRspSecond;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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












