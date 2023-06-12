package com.example.carsharingservice.telegrambot;

import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.UserService;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

import java.util.*;

@Component
public class NotificationBot extends TelegramLongPollingBot {
    private static final String BOT_NAME = "Car sharing service";
    private static final String REGEX = "email\\s*:\\s*(.*?),\\s*password\\s*:\\s*(.*)";
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public NotificationBot(UserService userService, PasswordEncoder passwordEncoder) {
        super(Dotenv.configure().load().get("TOKEN"));
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String input = update.getMessage().getText();
        Optional<User> user = userService.findByChatId(update.getMessage().getChatId());
        SendMessage messageToSend = new SendMessage();
        messageToSend.setChatId(update.getMessage().getChatId());
        if ("/start".equals(input)) {
            messageToSend.setText("Hi!)\n I'm a notification bot, " +
                    "write your email for authentication in one message, example: \n" +
                    "email : bob@gmail.com, " +
                    "password : 12345678");
            try {
                execute(messageToSend);
                return;
            } catch (TelegramApiException e) {
                throw new RuntimeException("Can't send message", e);
            }
        }
        if (user.isEmpty()) {
            if (input.matches(REGEX)) {
                List<String> emailPassword = parseInputMessage(input);
                Optional<User> validatingUser = userService.findByEmail(emailPassword.get(0));
                if (validatingUser.isPresent()
                        && passwordEncoder.matches(emailPassword.get(1),
                        validatingUser.get().getPassword())) {
                    User userToUpdate = validatingUser.get();
                    userToUpdate.setChatId(update.getMessage().getChatId());
                    userService.update(userToUpdate);
                    messageToSend.setText("Good! You are authenticated!");
                } else {
                    messageToSend.setText("Invalid email or password");
                }
            } else {
                messageToSend.setText("Invalid format for sending email and password, " +
                        "please check the example: \n" +
                        "email : bob@gmail.com, " +
                        "password : 12345678");
            }
        } else {
            messageToSend.setText("You are already authenticated");
        }
        try {
            execute(messageToSend);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Can't send message", e);
        }
    }

    private List<String> parseInputMessage(String message) {
        String[] parsedMessage = message.split(",");
        return Arrays.stream(parsedMessage)
                .map(l -> l.split(":")[1].trim())
                .toList();
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }
}
