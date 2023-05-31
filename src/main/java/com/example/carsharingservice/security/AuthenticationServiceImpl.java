package com.example.carsharingservice.security;

import com.example.carsharingservice.exception.AuthenticationException;
import com.example.carsharingservice.model.Role;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.UserService;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(String email, String password, String firstName, String lastName) throws AuthenticationException {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(Role.CUSTOMER);
        return userService.save(user);
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> user = userService.findByEmail(login);
        if (user.isEmpty() || !passwordEncoder.matches(password, user.get().getPassword())) {
            throw new AuthenticationException("Incorrect username or password!!!");
        }
        return user.get();
    }
}
