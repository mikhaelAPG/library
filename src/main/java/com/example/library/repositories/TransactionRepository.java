package com.example.library.repositories;

import com.example.library.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(value = "SELECT EXTRACT(DAY FROM(return_date-due_date)) AS DIff FROM transactions WHERE id = ?1", nativeQuery = true)
    public Integer getDifferentDate(Long id);
    List<Transaction> findAllByDeletedAtIsNull();
}
