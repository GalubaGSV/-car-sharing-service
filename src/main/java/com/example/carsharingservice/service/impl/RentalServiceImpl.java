package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.repository.RentalRepository;
import com.example.carsharingservice.service.CarService;
import com.example.carsharingservice.service.RentalService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RentalServiceImpl implements RentalService {
    private final RentalRepository rentalRepository;
    private final CarService carService;

    @Transactional
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
    public List<Rental> getRentalsByUserIdAndStatus(Long id, boolean status, Pageable pageable) {
        return rentalRepository.getRentalsByUserIdAndStatus(id, status, pageable);
    }

    @Override
    public Rental get(Long id) {
        return rentalRepository.getReferenceById(id);
    }

    @Transactional
    @Override
    public Rental returnCar(Long id, Rental rental) {
        Car carToReturn = carService.get(rental.getCar().getId());
        carToReturn.setInventory(carToReturn.getInventory() + 1);
        carService.update(carToReturn.getId(), carToReturn);
        rental.setActualReturnDate(LocalDateTime.now());
        return update(id, rental);
    }

    @Override
    public List<Rental> getOverdueRentals() {
        return rentalRepository.getOverdueRentals(LocalDateTime.now());
    }

    protected Rental update(Long id, Rental rental) {
        Rental rentalToUpdate = rentalRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("There is no rental with id: " + id)
        );
        rentalToUpdate.setRentalDate(rental.getRentalDate());
        rentalToUpdate.setReturnDate(rental.getReturnDate());
        rentalToUpdate.setActualReturnDate(rental.getActualReturnDate());
        rentalToUpdate.setCar(rental.getCar());
        rentalToUpdate.setUser(rental.getUser());
        return rentalRepository.save(rentalToUpdate);
    }
}
