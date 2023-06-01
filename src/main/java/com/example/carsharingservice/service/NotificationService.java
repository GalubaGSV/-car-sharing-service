package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Rental;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public interface NotificationService {
    void sendMessage(String text);
}
