package com.example.carsharingservice.service.mapper;

import com.example.carsharingservice.dto.request.CarRequestDto;
import com.example.carsharingservice.dto.response.CarResponseDto;
import com.example.carsharingservice.model.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper implements DtoMapper<CarRequestDto, CarResponseDto, Car> {
    @Override
    public Car mapToModel(CarRequestDto dto) {
        Car car = new Car();
        car.setCarType(dto.getCarType());
        car.setModel(dto.getModel());
        car.setBrand(dto.getBrand());
        car.setInventory(dto.getInventory());
        car.setDailyFee(dto.getDailyFee());
        return car;
    }

    @Override
    public CarResponseDto mapToDto(Car car) {
        CarResponseDto dto = new CarResponseDto();
        dto.setCarType(String.valueOf(car.getCarType()));
        dto.setId(car.getId());
        dto.setModel(car.getModel());
        dto.setBrand(car.getBrand());
        dto.setInventory(car.getInventory());
        dto.setDailyFee(car.getDailyFee());
        dto.setDeleted(car.isDeleted());
        return dto;
    }
}
