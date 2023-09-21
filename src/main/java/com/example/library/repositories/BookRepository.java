package com.example.library.repositories;

import com.example.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByDeletedAtIsNull();
    List<Book> findByIsbn(String isbn);
    @Query("SELECT b FROM Book b WHERE b.category = :category")
    List<Book> findByCategory(String category);
    @Query("SELECT b FROM Book b WHERE b.year = :year")
    List<Book> findByYear(String year);
    @Query("SELECT b FROM Book b WHERE b.publisher = :publisher")
    List<Book> findByPublisher(String publisher);
    @Query("SELECT b FROM Book b WHERE b.author = :author")
    List<Book> findByAuthor(String author);
}
