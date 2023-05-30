package com.example.carsharingservice.repository;

import com.example.carsharingservice.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    @Query("SELECT r FROM Rental r " +
            "WHERE (:isActive = true AND r.actualReturnDate IS NOT NULL AND r.user.id = :id) " +
            "OR (:isActive = false AND r.actualReturnDate IS NULL AND r.user.id = :id)")
    List<Rental> getRentalsByUserIdAndStatus(@Param("id") Long id, @Param("isActive") boolean isActive);
}
