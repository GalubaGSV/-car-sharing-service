package com.example.carsharingservice.mapper;

import com.example.carsharingservice.dto.request.RentalRequestDto;
import com.example.carsharingservice.dto.response.RentalResponseDto;
import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.model.User;
import org.springframework.stereotype.Component;

@Component
public class RentalMapper implements DtoMapper<RentalRequestDto, RentalResponseDto, Rental> {
    @Override
    public Rental mapToModel(RentalRequestDto dto) {
        Rental rental = new Rental();
        rental.setRentalDate(dto.getRentalDate());
        rental.setReturnDate(dto.getReturnDate());
        rental.setActualReturnDate(dto.getActualReturnDate());
        Car car = new Car();
        car.setId(dto.getCarId());
        rental.setCar(car);
        User user = new User();
        user.setId(dto.getUserId());
        rental.setUser(user);
        return rental;
    }

    @Override
    public RentalResponseDto mapToDto(Rental rental) {
        RentalResponseDto dto = new RentalResponseDto();
        dto.setId(rental.getId());
        dto.setRentalDate(rental.getRentalDate());
        dto.setReturnDate(rental.getReturnDate());
        dto.setActualReturnDate(rental.getActualReturnDate());
        dto.setCarId(rental.getCar().getId());
        dto.setUserId(rental.getUser().getId());
        return dto;
    }
}
