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
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/rentals")
public class RentalController {
    private final RentalService rentalService;
    private final DtoMapper<RentalRequestDto, RentalResponseDto, Rental> rentalMapper;
    private final DtoMapper<UserRequestDto, UserResponseDto, User> userMapper;
    private final CarService carService;
    private final UserService userService;
    private final TelegramNotificationService telegramNotificationService;

    @PostMapping("/")
    public RentalResponseDto add(@RequestBody RentalRequestDto rentalRequestDto) {
        Rental createdRental = rentalService.add(rentalMapper.mapToModel(rentalRequestDto));
        telegramNotificationService.sendMessage(String
                .format("New rental was created. \n" +
                                "Rental info: %s \n" +
                                "User info: %s \n" +
                                "Car info: %s", createdRental,
                        userMapper.mapToDto(userService.get(createdRental.getUser().getId())),
                        carService.get(createdRental.getCar().getId())));
        return rentalMapper.mapToDto(createdRental);
    }

    @GetMapping("/")
    public List<RentalResponseDto> getRentalsByUserIdAndStatus(@RequestParam(name = "user_id") Long id,
                                                               @RequestParam(name = "is_active") boolean isActive) {
        return rentalService.getRentalsByUserIdAndStatus(id, isActive)
                .stream()
                .map(rentalMapper::mapToDto)
                .toList();
    }

    @GetMapping("/{id}")
    public RentalResponseDto get(@PathVariable Long id) {
        return rentalMapper.mapToDto(rentalService.get(id));
    }

    @PostMapping("/{id}/return")
    public RentalResponseDto returnCar(@PathVariable Long id,
                                       @RequestBody RentalRequestDto rentalRequestDto) {
        Rental processedRental = rentalService
                .returnCarById(id, rentalMapper.mapToModel(rentalRequestDto));
        telegramNotificationService.sendMessage(String
                .format("The car was returned. \n" +
                                "Rental info: %s \n" +
                                "User info: %s \n" +
                                "Car info: %s", processedRental,
                        userMapper.mapToDto(userService.get(processedRental.getUser().getId())),
                        carService.get(processedRental.getCar().getId())));
        return rentalMapper.mapToDto(processedRental);
    }
}
