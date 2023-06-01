package com.example.carsharingservice.telegrambot;

import com.example.carsharingservice.telegrambot.model.TelegramChat;
import com.example.carsharingservice.telegrambot.service.TelegramChatService;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class NotificationBot extends TelegramLongPollingBot {
    private static final String BOT_NAME = "Car sharing service";
    private final TelegramChatService telegramChatService;

    public NotificationBot(TelegramChatService telegramChatService) {
        super(Dotenv.configure().load().get("TOKEN"));
        this.telegramChatService = telegramChatService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String command = update.getMessage().getText();
        if ("/start".equals(command)) {
            TelegramChat telegramChat = new TelegramChat();
            telegramChat.setChatId(update.getMessage().getChatId());
            telegramChatService.add(telegramChat);
        } else {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId());
            sendMessage.setText("Sorry, but I'm just a notification bot "
                    + "and can't communicate with you");
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }
}
