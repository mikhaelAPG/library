package com.example.library.seeders;

import com.example.library.model.Book;
import com.example.library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class BookSeeder {

    @Autowired
    private BookRepository bookRepository;
    @PostConstruct
    @Transactional
    public void seed() {
        List<Book> books = new ArrayList<>(Arrays.asList(
                new Book("112233445566", "The Great", "History", "Gramedia", "2014", "Boby", 4),
                new Book("112211446655", "The Duck", "Biology", "Gramedia", "2015", "Bob", 3),
                new Book("2211133445566", "The Money", "Finance", "Gramedia", "2012", "Mira", 5),
                new Book("2211133445511", "The Harry", "Comedy", "Erlangga", "2016", "Nathan", 1),
                new Book("3311133445566", "The Catcher", "Fantasy", "Erlangga", "2017", "Miguel", 3)
        ));

        if (bookRepository.findAllByDeletedAtIsNull().isEmpty()) {
            bookRepository.saveAll(books);
        }
    }
}
