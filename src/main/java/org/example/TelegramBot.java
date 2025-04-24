package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Random;

public class TelegramBot extends TelegramLongPollingBot {
    String key = "8075614842:AAFQlR_mOAHEtU2IANSb-G8P-WiH-C9oAZE";


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





           pazzle(chatId,userMessage);


       //     send(chatId);

        }else {
            System.out.println("Received non-text message");
        }
    }

    int pazzleNum = -1;
    private void pazzle(long chatId, String userMessage) {

        if (userMessage.equals("New")){
            System.out.println("123");
            pazzleNum = -1;
        }

        int num = Integer.valueOf(userMessage);

     if (pazzleNum == -1) {
         Random random = new Random();
         pazzleNum = random.nextInt(100);
         System.out.println(pazzleNum);
     }

         if (num > pazzleNum) {
             send(chatId, "Меньше");}
        if (num < pazzleNum) {
            send(chatId, "Більше");}
        if (num==pazzleNum) {
            send(chatId, "Перемога");
            pazzleNum = -1;
            send(chatId, "Нове число загадано");
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










