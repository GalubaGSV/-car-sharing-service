package com.example.carsharingservice.config;

import com.example.carsharingservice.model.Role;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DataInitializer {
    private final UserService userService;

    @PostConstruct
    public void inject() {
        User user = new User();
        user.setRole(Role.MANAGER);
        user.setEmail("manager@i.ua");
        user.setPassword("managertest");
        user.setFirstName("bob");
        user.setLastName("bobchenko");
        userService.save(user);
    }
}
