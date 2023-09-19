package com.example.library.controllers;

import com.example.library.dto.BookRequest;
import com.example.library.model.Book;
import com.example.library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("")
    public ResponseEntity getBooks() {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.bookList());
    }

    @GetMapping("/{id}")
    public ResponseEntity getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);

        if (book != null) {
            return ResponseEntity.status(HttpStatus.OK).body(book);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book Not Found");
        }
    }

    @PostMapping("")
    public ResponseEntity addBook(@RequestBody BookRequest request) {
        Book newBook = bookService.addBook(request);
        if (newBook != null) {
            return ResponseEntity.status(HttpStatus.OK).body( "Book Added Successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to Add Book.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateBook(@PathVariable long id, @RequestBody BookRequest request) {
        if (bookService.updateBook(id, request)) {
            return ResponseEntity.status(HttpStatus.OK).body("Major Updated Successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to Update Major.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBook(@PathVariable long id) {
        if (bookService.deleteBook(id)) {
            return ResponseEntity.status(HttpStatus.OK).body( "Major Deleted Successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to Delete Major.");
        }
    }
}
