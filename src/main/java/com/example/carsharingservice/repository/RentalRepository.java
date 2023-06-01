package com.example.carsharingservice.repository;

import com.example.carsharingservice.model.Rental;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    @Query("SELECT r FROM Rental r "
            + "WHERE (:isActive = false AND r.actualReturnDate IS NOT NULL AND r.user.id = :id) "
            + "OR (:isActive = true AND r.actualReturnDate IS NULL AND r.user.id = :id)")
    List<Rental> getRentalsByUserIdAndStatus(@Param("id") Long id,
                                             @Param("isActive") boolean isActive,
                                             Pageable pageable);

    @Query("SELECT r FROM Rental r "
            + "LEFT JOIN FETCH r.user "
            + "LEFT JOIN FETCH r.car "
            + "WHERE r.returnDate < :date_now AND r.actualReturnDate IS NULL")
    List<Rental> getOverdueRentals(@Param("date_now") LocalDateTime dateTime);
}
