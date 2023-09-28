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
    @Query(value = "SELECT b.title AS book_title, COUNT(*) AS total_borrowing FROM transactions t JOIN books b ON t.book_id = b.id JOIN users u ON t.user_id = u.id WHERE b.deleted_at IS NULL AND u.deleted_at IS NULL GROUP BY b.title ORDER BY total_borrowing ASC LIMIT 5", nativeQuery = true)
    List<Object[]> findTop5MostBorrowedBooks();

    @Query(value = "SELECT u.name, COUNT(*) AS total_borrowed_books FROM transactions t JOIN users u ON t.user_id = u.id WHERE EXTRACT(MONTH FROM t.borrowing_date) = ?1 AND u.deleted_at IS NULL GROUP BY u.name ORDER BY total_borrowed_books ASC LIMIT 3", nativeQuery = true)
    List<Object[]> findTop3MembersMostBorrowedBooksInMonth(int month);

    @Query(value = "SELECT u.name, SUM(t.penalty) AS total_penalty FROM transactions t JOIN users u ON t.user_id = u.id WHERE t.return_date IS NOT NULL AND u.deleted_at IS NULL GROUP BY u.name ORDER BY total_penalty ASC LIMIT 3", nativeQuery = true)
    List<Object[]> findTop3MembersMostLateReturns();
}
