package com.example.carsharingservice.service.mapper;

import static org.springframework.test.util.AssertionErrors.assertEquals;

import com.example.carsharingservice.dto.request.RentalRequestDto;
import com.example.carsharingservice.dto.response.RentalResponseDto;
import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.model.CarType;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.model.Role;
import com.example.carsharingservice.model.User;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class RentalMapperTest {
    private static final LocalDateTime RENTAL_DATE = LocalDateTime.now().plusDays(1);
    private static final LocalDateTime RETURN_DATE = RENTAL_DATE.plusDays(2);
    private static final LocalDateTime ACTUAL_RETURN_DATE = RETURN_DATE.plusHours(1);
    private static final long USER_ID = 1L;
    private static final long CAR_ID = 1L;
    private static final long RENTAL_ID = 1L;
    private static final long CHAT_ID = 1L;
    private static final int INVENTORY = 20;
    private static final boolean IS_DELETED = false;
    private static final Role ROLE = Role.CUSTOMER;
    private static final CarType CAR_TYPE = CarType.SEDAN;
    private static final BigDecimal DAILY_FEE = BigDecimal.valueOf(20L);
    private static final String EMAIL = "qwerty123@mail.com";
    private static final String PASSWORD = "qwerty123";
    private static final String MODEL = "Camry";
    private static final String BRAND = "Toyota";
    private static final String LASTNAME = "Alice";
    private static final String FIRSTNAME = "Bobson";
    private static final String TEST_MESSAGE = "Expected true, but was false: ";
    private final DtoMapper<RentalRequestDto, RentalResponseDto, Rental> rentalMapper =
            new RentalMapper();
    private final RentalRequestDto rentalRequestDto = getRentalRequestDto();
    private final Rental rental = getRental();

    @Test
    void testToModel_ok() {
        Rental mapToModel = rentalMapper.mapToModel(rentalRequestDto);

        assertEquals(TEST_MESSAGE,
                RENTAL_DATE, mapToModel.getRentalDate());
        assertEquals(TEST_MESSAGE,
                RETURN_DATE, mapToModel.getReturnDate());
        assertEquals(TEST_MESSAGE,
                ACTUAL_RETURN_DATE, mapToModel.getActualReturnDate());
        assertEquals(TEST_MESSAGE,
                CAR_ID, mapToModel.getCar().getId());
        assertEquals(TEST_MESSAGE,
                USER_ID, mapToModel.getUser().getId());
    }

    @Test
    void testToDto_ok() {
        RentalResponseDto mapToDto = rentalMapper.mapToDto(rental);

        assertEquals(TEST_MESSAGE,
                RENTAL_ID, mapToDto.getId());
        assertEquals(TEST_MESSAGE,
                RENTAL_DATE, mapToDto.getRentalDate());
        assertEquals(TEST_MESSAGE,
                RETURN_DATE, mapToDto.getReturnDate());
        assertEquals(TEST_MESSAGE,
                ACTUAL_RETURN_DATE, mapToDto.getActualReturnDate());
        assertEquals(TEST_MESSAGE,
                CAR_ID, mapToDto.getCarId());
        assertEquals(TEST_MESSAGE,
                USER_ID, mapToDto.getUserId());
    }

    private static RentalRequestDto getRentalRequestDto() {
        RentalRequestDto rentalRequestDto = new RentalRequestDto();
        rentalRequestDto.setRentalDate(RENTAL_DATE);
        rentalRequestDto.setReturnDate(RETURN_DATE);
        rentalRequestDto.setActualReturnDate(ACTUAL_RETURN_DATE);
        rentalRequestDto.setCarId(CAR_ID);
        rentalRequestDto.setUserId(USER_ID);
        return rentalRequestDto;
    }

    private static Rental getRental() {
        Rental rental = new Rental();
        rental.setId(RENTAL_ID);
        rental.setUser(getUser());
        rental.setCar(getCar());
        rental.setReturnDate(RETURN_DATE);
        rental.setRentalDate(RENTAL_DATE);
        rental.setActualReturnDate(ACTUAL_RETURN_DATE);
        return rental;
    }

    private static User getUser() {
        User user = new User();
        user.setId(USER_ID);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        user.setLastName(LASTNAME);
        user.setFirstName(FIRSTNAME);
        user.setChatId(CHAT_ID);
        user.setRole(ROLE);
        return user;
    }

    private static Car getCar() {
        Car car = new Car();
        car.setId(CAR_ID);
        car.setInventory(INVENTORY);
        car.setCarType(CAR_TYPE);
        car.setModel(MODEL);
        car.setBrand(BRAND);
        car.setDeleted(IS_DELETED);
        car.setDailyFee(DAILY_FEE);
        return car;
    }
}
