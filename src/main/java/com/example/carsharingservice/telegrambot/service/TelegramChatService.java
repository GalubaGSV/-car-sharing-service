package com.example.carsharingservice.telegrambot.service;

import com.example.carsharingservice.telegrambot.model.TelegramChat;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface TelegramChatService {
    TelegramChat add(TelegramChat chat);

    List<TelegramChat> getAll();
}
