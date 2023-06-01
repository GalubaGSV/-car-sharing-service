package com.example.carsharingservice.telegrambot.service;

import com.example.carsharingservice.telegrambot.model.TelegramChat;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface TelegramChatService {
    TelegramChat add(TelegramChat chat);

    List<TelegramChat> getAll();
}
