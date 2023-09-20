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
    @Query(value = "SELECT t.book_id, COUNT(*) AS total_borrowing FROM transactions t GROUP BY t.book_id ORDER BY total_borrowing DESC LIMIT 5", nativeQuery = true)
    List<Object[]> findTop5MostBorrowedBooks();
    @Query(value = "SELECT user_id, COUNT(*) as total_borrowed_books FROM transactions WHERE EXTRACT(MONTH FROM borrowing_date) = 12 GROUP BY user_id ORDER BY total_borrowed_books DESC LIMIT 3", nativeQuery = true)
    List<Object[]> findTop3MembersMostBorrowedBooksInMonth(int month);
    @Query(value = "SELECT user_id, SUM(penalty) as total_penalty FROM transactions WHERE return_date IS NOT NULL GROUP BY user_id ORDER BY total_penalty DESC LIMIT 3", nativeQuery = true)
    List<Object[]> findTop3MembersMostLateReturns();
}
