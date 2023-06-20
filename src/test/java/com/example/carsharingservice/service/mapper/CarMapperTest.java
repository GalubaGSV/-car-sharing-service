package com.example.carsharingservice.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.carsharingservice.dto.request.CarRequestDto;
import com.example.carsharingservice.dto.response.CarResponseDto;
import com.example.carsharingservice.model.Car;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class CarMapperTest {
    private static final Long CAR_ID = 1L;
    private static final String MODEL = "X";
    private static final String BRAND = "Tesla";
    private static final int DAILY_FEE = 150;
    private static final int INVENTORY = 10;
    private final static CarMapper mapper = new CarMapper();
    private final static CarResponseDto responseDto = getResponseDto();
    private final static CarRequestDto requestDto = getRequestDto();
    private static final Car car = getCar();

    @Test
    void testMap_toDto() {
        CarResponseDto result = mapper.mapToDto(car);

        assertEquals(responseDto, result);
    }

    @Test
    void testMap_toModel() {
        Car result = mapper.mapToModel(requestDto);

        assertEquals(MODEL, result.getModel());
        assertEquals(BRAND, result.getBrand());
        assertEquals(Car.CarType.UNIVERSAL, result.getCarType());
        assertEquals(INVENTORY, result.getInventory());
        assertEquals(BigDecimal.valueOf(DAILY_FEE), result.getDailyFee());
    }

    private static Car getCar() {
        Car car = new Car();
        car.setId(CAR_ID);
        car.setModel(MODEL);
        car.setBrand(BRAND);
        car.setCarType(Car.CarType.UNIVERSAL);
        car.setDailyFee(BigDecimal.valueOf(DAILY_FEE));
        car.setInventory(INVENTORY);
        car.setDeleted(false);
        return car;
    }

    private static CarResponseDto getResponseDto() {
        CarResponseDto dto = new CarResponseDto();
        dto.setId(CAR_ID);
        dto.setModel(MODEL);
        dto.setBrand(BRAND);
        dto.setCarType("UNIVERSAL");
        dto.setDailyFee(DAILY_FEE + " USD");
        dto.setInventory(INVENTORY);
        dto.setDeleted(false);
        return dto;
    }

    private static CarRequestDto getRequestDto() {
        CarRequestDto requestDto = new CarRequestDto();
        requestDto.setModel(MODEL);
        requestDto.setBrand(BRAND);
        requestDto.setCarType(Car.CarType.UNIVERSAL);
        requestDto.setInventory(INVENTORY);
        requestDto.setDailyFee(BigDecimal.valueOf(DAILY_FEE));
        return requestDto;
    }
}
