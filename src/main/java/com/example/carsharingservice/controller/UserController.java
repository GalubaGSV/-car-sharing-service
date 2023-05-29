package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.request.UserRequestDto;
import com.example.carsharingservice.dto.response.UserResponseDto;
import com.example.carsharingservice.model.Role;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.UserService;
import com.example.carsharingservice.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper mapper;

    @PutMapping("/{id}/role")
    public UserResponseDto updateRole(@PathVariable Long id, @RequestParam String role) {
        User user = userService.get(id);
        user.setRole(Role.valueOf(role));
        return mapper.mapToDto(userService.update(user));
    }

    @GetMapping("/me")
    public UserResponseDto get(Authentication auth) {
        UserDetails details = (UserDetails) auth.getPrincipal();
        String email = details.getUsername();
        return mapper.mapToDto(userService.findByEmail(email));
    }

    @PutMapping("/me")
    public UserResponseDto updateInfo(Authentication auth, UserRequestDto dto) {
        UserDetails details = (UserDetails) auth.getPrincipal();
        String email = details.getUsername();
        Long userId = userService.findByEmail(email).getId();
        User user = mapper.mapToModel(dto);
        user.setId(userId);
        return mapper.mapToDto(userService.update(user));
    }
}
