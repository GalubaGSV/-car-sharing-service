package com.example.carsharingservice.telegrambot.service.impl;

import com.example.carsharingservice.telegrambot.model.TelegramChat;
import com.example.carsharingservice.telegrambot.repository.TelegramChatRepository;
import com.example.carsharingservice.telegrambot.service.TelegramChatService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@AllArgsConstructor
@Service
public class TelegramChatServiceImpl  implements TelegramChatService {
    private final TelegramChatRepository telegramChatRepository;

    @Override
    public TelegramChat add(TelegramChat chat) {
        return telegramChatRepository.save(chat);
    }

    @Override
    public List<TelegramChat> getAll() {
        return telegramChatRepository.findAll();
    }
}
