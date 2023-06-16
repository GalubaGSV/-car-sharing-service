package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.dto.request.CarRequestDto;
import com.example.carsharingservice.dto.request.RentalRequestDto;
import com.example.carsharingservice.dto.request.UserRequestDto;
import com.example.carsharingservice.dto.response.CarResponseDto;
import com.example.carsharingservice.dto.response.RentalResponseDto;
import com.example.carsharingservice.dto.response.UserResponseDto;
import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.NotificationService;
import com.example.carsharingservice.service.RentalService;
import com.example.carsharingservice.service.UserService;
import com.example.carsharingservice.service.mapper.DtoMapper;
import com.example.carsharingservice.telegrambot.NotificationBot;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@AllArgsConstructor
@Service
public class TelegramNotificationService implements NotificationService {
    private final RentalService rentalService;
    private final UserService userService;
    private final NotificationBot notificationBot;
    private final DtoMapper<UserRequestDto, UserResponseDto, User> userMapper;
    private final DtoMapper<RentalRequestDto, RentalResponseDto, Rental> rentalMapper;
    private final DtoMapper<CarRequestDto, CarResponseDto, Car> carMapper;

    @Override
    public void sendMessage(String text, User user) {
        if (user.getChatId() != null) {
            SendMessage message = new SendMessage();
            message.setChatId(user.getChatId());
            message.setText(text);
            try {
                notificationBot.execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException("Can't send the message", e);
            }
        }
    }

    @Scheduled(cron = "0 1 0 * * ?")
    public void checkOverdueRentals() {
        List<Rental> overdueRentals = rentalService.getOverdueRentals();
        if (!overdueRentals.isEmpty()) {
            for (Rental rental : overdueRentals) {
                sendMessage(String.format(
                        "Attention, the rental period has expired for you: %s \n"
                                + "full rental info: %s \n"
                                + "car info: %s \n", userMapper.mapToDto(rental.getUser()),
                        rentalMapper.mapToDto(rental), carMapper.mapToDto(rental.getCar())),
                        rental.getUser());
            }
        } else {
            List<User> usersWithChatId = userService.findAllWithChatId();
            for (User user : usersWithChatId) {
                sendMessage("No rentals overdue today", user);
            }
        }
    }
}
