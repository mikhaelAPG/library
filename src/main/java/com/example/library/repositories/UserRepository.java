package com.example.library.repositories;

import com.example.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByDeletedAtIsNull();
    Optional<User> findByIdAndDeletedAtIsNull(Long id);
    @Query("SELECT u FROM User u WHERE u.type = :type AND u.deletedAt IS NULL")
    List<User> findByType(String type);
}
