package com.example.carsharingservice.repository;

import com.example.carsharingservice.model.Role;
import com.example.carsharingservice.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    Optional<User> findByChatId(Long chatId);

    List<User> findAllByRole(Role role);

    @Query("SELECT u FROM User u WHERE u.chatId IS NOT NULL")
    List<User> findAllWithChatId();
}
