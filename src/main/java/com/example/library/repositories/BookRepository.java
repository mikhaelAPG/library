package com.example.library.repositories;

import com.example.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByDeletedAtIsNull();
    Optional<Book> findByIdAndDeletedAtIsNull(Long id);
    List<Book> findByIsbn(String isbn);
    @Query("SELECT b FROM Book b WHERE b.category = :category AND b.deletedAt IS NULL")
    List<Book> findByCategory(String category);
    @Query("SELECT b FROM Book b WHERE b.year = :year AND b.deletedAt IS NULL")
    List<Book> findByYear(String year);
    @Query("SELECT b FROM Book b WHERE b.publisher = :publisher AND b.deletedAt IS NULL")
    List<Book> findByPublisher(String publisher);
    @Query("SELECT b FROM Book b WHERE b.author = :author AND b.deletedAt IS NULL")
    List<Book> findByAuthor(String author);
}
