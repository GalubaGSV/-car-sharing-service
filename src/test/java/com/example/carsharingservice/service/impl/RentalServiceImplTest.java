package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.repository.RentalRepository;
import com.example.carsharingservice.service.CarService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RentalServiceImplTest {
    private static final Long RENTAL_ID = 1L;
    @InjectMocks
    private RentalServiceImpl rentalService;
    @Mock
    private RentalRepository rentalRepository;
    @Mock
    private CarService carService;


    @Test
    void testAddRental_ok() {
        Car bookedCar = new Car();
        bookedCar.setInventory(1);
        Rental rental = new Rental();
        rental.setCar(bookedCar);

        when(carService.get(rental.getCar().getId())).thenReturn(bookedCar);
        when(rentalRepository.save(rental)).thenReturn(rental);

        Rental result = rentalService.add(rental);
        int resultCarInventory = result.getCar().getInventory();

        assertEquals(rental, result);
        assertEquals(0, resultCarInventory);
    }

    @Test
    void testAddRental_noInventory_throwsException() {
        Car bookedCar = new Car();
        bookedCar.setInventory(0);
        Rental rental = new Rental();
        rental.setCar(bookedCar);

        when(carService.get(rental.getCar().getId())).thenReturn(bookedCar);

        assertThrows(RuntimeException.class, () -> rentalService.add(rental));
        assertEquals(0, bookedCar.getInventory());
    }

    @Test
    void testUpdateRental_ok() {
        Rental existingRental = new Rental();
        existingRental.setId(RENTAL_ID);
        Rental updatedRental = new Rental();
        updatedRental.setId(RENTAL_ID);

        when(rentalRepository.findById(RENTAL_ID)).thenReturn(Optional.of(existingRental));
        when(rentalRepository.save(any(Rental.class))).thenReturn(updatedRental);

        Rental result = rentalService.update(RENTAL_ID, updatedRental);

        assertEquals(updatedRental, result);
    }

    @Test
    void testUpdateRental_notFound_ThrowsException() {
        Rental updatedRental = new Rental();
        updatedRental.setId(RENTAL_ID);

        when(rentalRepository.findById(RENTAL_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> rentalService.update(RENTAL_ID, updatedRental));
    }
}