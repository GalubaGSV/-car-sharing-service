package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.dto.request.UserRequestDto;
import com.example.carsharingservice.dto.response.UserResponseDto;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.NotificationService;
import com.example.carsharingservice.service.RentalService;
import com.example.carsharingservice.service.mapper.DtoMapper;
import com.example.carsharingservice.telegrambot.NotificationBot;
import com.example.carsharingservice.telegrambot.model.TelegramChat;
import com.example.carsharingservice.telegrambot.service.TelegramChatService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.List;

@AllArgsConstructor
@Service
public class TelegramNotificationService implements NotificationService {
    private final RentalService rentalService;
    private final TelegramChatService telegramChatService;
    private final NotificationBot notificationBot;
    private final DtoMapper<UserRequestDto, UserResponseDto, User> userMapper;

    @Override
    public void sendMessage(String text) {
        List<TelegramChat> telegramChats = telegramChatService.getAll();
        if (!telegramChats.isEmpty()) {
            for (TelegramChat chat : telegramChats) {
                SendMessage message = new SendMessage();
                message.setChatId(chat.getChatId());
                message.setText(text);
                try {
                    notificationBot.execute(message);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Scheduled(cron = "0 1 0 * * ?")
    public void checkOverdueRentals() {
        List<Rental> overdueRentals = rentalService.getOverdueRentals();
        if (!overdueRentals.isEmpty()) {
            for (Rental rental : overdueRentals) {
                sendMessage(String.format(" Attention, the rental period has expired for the user: %s,\n "
                                + "full rental info: %s\n "
                        + "car info: %s", userMapper.mapToDto(rental.getUser()), rental, rental.getCar()));
            }
        } else {
            sendMessage("No rentals overdue today");
        }
    }
}
