package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.request.UserRequestDto;
import com.example.carsharingservice.dto.response.UserResponseDto;
import com.example.carsharingservice.model.Role;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.UserService;
import com.example.carsharingservice.service.mapper.DtoMapper;
import java.util.NoSuchElementException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final DtoMapper<UserRequestDto, UserResponseDto, User> userMapper;

    @PutMapping("/{id}/role")
    public UserResponseDto updateRole(@PathVariable Long id, @RequestParam String role) {
        try {
            Role.valueOf(role);
            User user = userService.get(id);
            user.setRole(Role.valueOf(role));
            return userMapper.mapToDto(userService.update(user));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("There is no such role!", e);
        }

    }

    @GetMapping("/me")
    public UserResponseDto get(Authentication auth) {
        UserDetails details = (UserDetails) auth.getPrincipal();
        String email = details.getUsername();
        return userMapper.mapToDto(userService.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User doesn't exist!")));
    }

    @PutMapping("/me")
    public UserResponseDto updateInfo(Authentication auth, UserRequestDto dto) {
        UserDetails details = (UserDetails) auth.getPrincipal();
        String email = details.getUsername();
        Long userId = userService.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User doesn't exist!")).getId();
        User user = userMapper.mapToModel(dto);
        user.setId(userId);
        return userMapper.mapToDto(userService.update(user));
    }
}
