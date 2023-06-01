package com.example.carsharingservice.telegrambot.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "telegram_chats")
public class TelegramChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private Long chatId;
}
