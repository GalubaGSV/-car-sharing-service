package com.example.carsharingservice.telegrambot.repository;

import com.example.carsharingservice.telegrambot.model.TelegramChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelegramChatRepository extends JpaRepository<TelegramChat, Long> {
}
