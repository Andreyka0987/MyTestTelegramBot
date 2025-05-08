package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new TelegramBot());
            System.out.println("Бот запущено!");
        } catch (Exception e) {
            e.printStackTrace();
        }

//
//        int[][] array = {
//                {1, 2, 3, 4, 5},
//                {6, 7, 8, 9, 10},
//                {11, 12, 13, 14, 15},
//                {16, 17, 18, 19, 20}
//        };
//
//
//        System.out.println(array[2][0]);


//        for (int i = 0;i<array.length; i++){
//            for (int j = 0;j<array.length+1; j++){
//                System.out.println(array[i][j]);
//            }
//        }


    }
}