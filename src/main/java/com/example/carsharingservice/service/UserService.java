package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Role;
import com.example.carsharingservice.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User update(User user);

    User get(Long id);

    Optional<User> findByEmail(String email);

    User save(User user);

    Optional<User> findByChatId(Long chatId);

    List<User> findAllByRole(Role role);

    List<User> findAllWithChatId();
}
