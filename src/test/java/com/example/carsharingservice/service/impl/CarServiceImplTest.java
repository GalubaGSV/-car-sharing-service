package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.repository.CarRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarServiceImplTest {
    private static final Long EXISTING_CAR_ID = 1L;
    private static final Long NON_EXISTING_CAR_ID = 999L;
    private static final String MODEL = "X";
    private static final String BRAND = "Tesla";
    private static final int DAILY_FEE = 150;
    private static final int INVENTORY = 10;
    private static final String UPDATED_MODEL = "Model";
    private static final String UPDATED_BRAND = "Brand";
    private static final int UPDATED_DAILY_FEE = 100;
    private static final int UPDATED_INVENTORY = 15;
    @Mock
    private CarRepository carRepository;
    @InjectMocks
    private CarServiceImpl carService;

    @Test
    void testUpdate_isOk() {
        Car existingCar = new Car();
        existingCar.setId(EXISTING_CAR_ID);
        existingCar.setModel(MODEL);
        existingCar.setBrand(BRAND);
        existingCar.setCarType(Car.CarType.UNIVERSAL);
        existingCar.setDailyFee(BigDecimal.valueOf(DAILY_FEE));
        existingCar.setInventory(INVENTORY);

        Car updatedCar = new Car();
        updatedCar.setId(EXISTING_CAR_ID);
        updatedCar.setModel(UPDATED_MODEL);
        updatedCar.setBrand(UPDATED_BRAND);
        updatedCar.setCarType(Car.CarType.SUV);
        updatedCar.setDailyFee(BigDecimal.valueOf(UPDATED_DAILY_FEE));
        updatedCar.setInventory(UPDATED_INVENTORY);

        when(carRepository.findById(EXISTING_CAR_ID)).thenReturn(java.util.Optional.of(existingCar));
        when(carRepository.save(any(Car.class))).thenReturn(updatedCar);

        Car result = carService.update(EXISTING_CAR_ID, updatedCar);

        assertEquals(updatedCar, result);
    }

    @Test
    void testUpdate_nonExistingCar_notOk() {
        Car nonExistingCar = new Car();
        nonExistingCar.setId(NON_EXISTING_CAR_ID);

        when(carRepository.findById(NON_EXISTING_CAR_ID)).thenReturn(java.util.Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> carService.update(NON_EXISTING_CAR_ID, nonExistingCar));
    }
}
