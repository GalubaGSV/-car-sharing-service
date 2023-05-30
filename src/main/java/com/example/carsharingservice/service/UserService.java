package com.example.carsharingservice.service;

import com.example.carsharingservice.model.User;
import java.util.Optional;

public interface UserService {
    User update(User user);

    User get(Long id);

    Optional<User> findByEmail(String email);

    User save(User user);
}
