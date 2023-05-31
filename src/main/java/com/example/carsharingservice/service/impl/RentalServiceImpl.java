package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.repository.RentalRepository;
import com.example.carsharingservice.service.CarService;
import com.example.carsharingservice.service.RentalService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class RentalServiceImpl implements RentalService {
    private final RentalRepository rentalRepository;
    private final CarService carService;

    @Override
    public Rental add(Rental rental) {
        Car bookedCar = carService.get(rental.getCar().getId());
        if (bookedCar.getInventory() > 0) {
            bookedCar.setInventory(bookedCar.getInventory() - 1);
            carService.update(bookedCar.getId(), bookedCar);
            return rentalRepository.save(rental);
        }
        throw new RuntimeException("All cars of this type of employment");
    }

    @Override
    public List<Rental> getRentalsByUserIdAndStatus(Long id, boolean status) {
        return rentalRepository.getRentalsByUserIdAndStatus(id, status);
    }

    @Override
    public Rental get(Long id) {
        return rentalRepository.getReferenceById(id);
    }

    @Override
    public Rental returnCarById(Long id, Rental rental) {
        Car carToReturn = carService.get(rental.getCar().getId());
        carToReturn.setInventory(carToReturn.getInventory() + 1);
        carService.update(carToReturn.getId(), carToReturn);
        rental.setActualReturnDate(LocalDateTime.now());
        rentalRepository.save(rental);
        return rental;
    }

    @Override
    public List<Rental> getOverdueRentals() {
        return rentalRepository.getOverdueRentals(LocalDateTime.now());
    }
}
