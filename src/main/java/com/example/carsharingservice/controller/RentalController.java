package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.request.RentalRequestDto;
import com.example.carsharingservice.dto.request.UserRequestDto;
import com.example.carsharingservice.dto.response.RentalResponseDto;
import com.example.carsharingservice.dto.response.UserResponseDto;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.CarService;
import com.example.carsharingservice.service.RentalService;
import com.example.carsharingservice.service.UserService;
import com.example.carsharingservice.service.impl.TelegramNotificationService;
import com.example.carsharingservice.service.mapper.DtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/rentals")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Rental", description = "The Rental API. "
        + "Contains all the operations that can be performed on a customer/manager.")
public class RentalController {
    private final RentalService rentalService;
    private final DtoMapper<RentalRequestDto, RentalResponseDto, Rental> rentalMapper;
    private final DtoMapper<UserRequestDto, UserResponseDto, User> userMapper;
    private final CarService carService;
    private final UserService userService;
    private final TelegramNotificationService telegramNotificationService;

    @Operation(summary = "Add rental", description = "Add rental")
    @PostMapping
    public RentalResponseDto add(@RequestBody RentalRequestDto rentalRequestDto) {
        Rental createdRental = rentalService.add(rentalMapper.mapToModel(rentalRequestDto));
        User userFromDb = userService.get(createdRental.getUser().getId());
        telegramNotificationService.sendMessage(String
                .format(
                                "New rental was created.\n"
                                        + "Rental info: %s\n"
                                        + "User info: %s\n"
                                        + "Car info: %s", rentalMapper.mapToDto(createdRental),
                        userMapper.mapToDto(userFromDb),
                        carService.get(createdRental.getCar().getId())), userFromDb);
        return rentalMapper.mapToDto(createdRental);
    }

    @Operation(summary = "Get rental by user and status",
            description = "Get rental by user and status")
    @GetMapping
    public List<RentalResponseDto> getRentalsByUserIdAndStatus(
            @RequestParam(name = "user_id") Long id,
            @RequestParam(name = "is_active") boolean isActive,
            @RequestParam(defaultValue = "20") Integer count,
            @RequestParam(defaultValue = "0") Integer page) {
        Pageable pageRequest = PageRequest.of(page, count);
        return rentalService.getRentalsByUserIdAndStatus(id, isActive, pageRequest)
                .stream()
                .map(rentalMapper::mapToDto)
                .toList();
    }

    @Operation(summary = "Get rental by id", description = "Get rental by id")
    @GetMapping("/{id}")
    public RentalResponseDto get(@PathVariable Long id) {
        return rentalMapper.mapToDto(rentalService.get(id));
    }

    @Operation(summary = "Set actual return date ", description = "Set actual return date ")
    @PostMapping("/{id}/return")
    public RentalResponseDto returnCar(@PathVariable Long id,
                                       @RequestBody RentalRequestDto rentalRequestDto) {
        Rental processedRental = rentalService
                .returnCar(id, rentalMapper.mapToModel(rentalRequestDto));
        User userFromDb = userService.get(processedRental.getUser().getId());
        telegramNotificationService.sendMessage(String
                .format(
                        "The car was returned.\n"
                                + "Rental info: %s\n"
                                + "User info: %s\n"
                                + "Car info: %s\n", rentalMapper.mapToDto(processedRental),
                        userMapper.mapToDto(userFromDb),
                        carService.get(processedRental.getCar().getId())), userFromDb);
        return rentalMapper.mapToDto(processedRental);
    }
}
