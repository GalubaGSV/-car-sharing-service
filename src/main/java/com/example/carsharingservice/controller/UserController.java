package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.request.UserRequestDto;
import com.example.carsharingservice.dto.response.UserResponseDto;
import com.example.carsharingservice.model.Role;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.UserService;
import com.example.carsharingservice.service.mapper.DtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final DtoMapper<UserRequestDto, UserResponseDto, User> userMapper;

    @Operation(summary = "Update user role", description = "Update user role")
    @PutMapping("/{id}/role")
    public UserResponseDto updateRole(
            @Parameter(description = "User id",
            schema = @Schema(type = "integer", defaultValue = "1"))
            @PathVariable Long id,
            @Parameter(description = "User role (Customer, Manager)",
            schema = @Schema(type = "string", defaultValue = "Customer"))
            @RequestParam String role) {
        try {
            Role.valueOf(role);
            User user = userService.get(id);
            user.setRole(Role.valueOf(role));
            return userMapper.mapToDto(userService.update(user));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("There is no such role!" + e);
        }

    }

    @Operation(summary = "Get current user info", description = "Get current user info")
    @GetMapping("/me")
    public UserResponseDto get(Authentication auth) {
        UserDetails details = (UserDetails) auth.getPrincipal();
        String email = details.getUsername();
        return userMapper.mapToDto(userService.findByEmail(email).get());
    }

    @Operation(summary = "Update current user info", description = "Update current user info")
    @PutMapping("/me")
    public UserResponseDto updateInfo(Authentication auth,
                                      @Parameter(schema = @Schema(type = "String",
                                              defaultValue = "{\n"
                                                      + "  \"email\": \"email@gmail.com\",\n"
                                                      + "  \"firstName\": \"Bob\",\n"
                                                      + "  \"lastName\": \"Bobson\",\n"
                                                      + "  \"password\": \"12345678\",\n"
                                                      + "  \"role\": \"MANAGER\"\n"
                                                      + "}"))
                                      @RequestBody UserRequestDto dto) {
        UserDetails details = (UserDetails) auth.getPrincipal();
        String email = details.getUsername();
        Long userId = userService.findByEmail(email).get().getId();
        User user = userMapper.mapToModel(dto);
        user.setId(userId);
        return userMapper.mapToDto(userService.update(user));
    }
}
