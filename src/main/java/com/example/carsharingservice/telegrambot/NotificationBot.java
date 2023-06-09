package com.example.carsharingservice.telegrambot;

import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.UserService;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.Optional;

@Component
public class NotificationBot extends TelegramLongPollingBot {
    private static final String BOT_NAME = "Car sharing service";
    private final UserService userService;

    public NotificationBot(UserService userService) {
        super(Dotenv.configure().load().get("TOKEN"));
        this.userService = userService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String command = update.getMessage().getText();
        Optional<User> user = userService.findByChatId(update.getMessage().getChatId());
        if ("/start".equals(command)) {
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId());
            message.setText("Hi!)\n I'm a notification bot, write your email for authentication");
        }
        if (user.isEmpty()) {

        }
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }
}
