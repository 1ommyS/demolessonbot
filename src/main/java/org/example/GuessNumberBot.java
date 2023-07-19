package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Random;

public class GuessNumberBot extends TelegramLongPollingBot {

    private final String botUsername = "demolessonbot";
    private final String botToken = "5776948435:AAFrobB1IPD9bjh5AGBmMV8LyrdCY9xYDCU";
    private final int maxNumber = 100;
    private final Random random = new Random();
    private int secretNumber;

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message.hasText()) {
            long chatId = message.getChatId();
            String text = message.getText();

            if ("/start".equals(text)) {
                secretNumber = random.nextInt(maxNumber) + 1;
                sendReply(chatId, "Привет! Я загадал число от 1 до " + maxNumber + ". Попробуй угадать!");
            } else if (isNumeric(text)) {
                int guessedNumber = Integer.parseInt(text);
                if (guessedNumber == secretNumber) {
                    sendReply(chatId, "Поздравляю, ты угадал число!");
                } else if (guessedNumber < secretNumber) {
                    sendReply(chatId, "Загаданное число больше " + guessedNumber);
                } else {
                    sendReply(chatId, "Загаданное число меньше " + guessedNumber);
                }
            } else {
                sendReply(chatId, "Я ожидаю число от 1 до " + maxNumber + ". Попробуй еще раз.");
            }
        }
    }

    private void sendReply(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+");
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
