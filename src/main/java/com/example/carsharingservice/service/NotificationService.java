package com.example.carsharingservice.service;

import com.example.carsharingservice.model.User;

public interface NotificationService {
    void sendMessage(String text, User user);
}
