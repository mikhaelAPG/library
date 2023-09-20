package com.example.library.services;

import com.example.library.dto.TransactionRequest;
import com.example.library.model.Book;
import com.example.library.model.Transaction;
import com.example.library.model.User;
import com.example.library.repositories.BookRepository;
import com.example.library.repositories.TransactionRepository;
import com.example.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    public List<Transaction> transactionList() {
        return transactionRepository.findAll();
    }

    // Fungsi untuk menambahkan data transaksi
    public Boolean addTransaction(TransactionRequest request) throws ParseException {
        Optional<User> user = userRepository.findById(request.getUserId());
        Optional<Book> book = bookRepository.findById(request.getBookId());

        if (user.isPresent() && book.isPresent()) {
            Date borrowingDate = new SimpleDateFormat("dd/MM/yyyy").parse(request.getBorrowingDate());
            Date dueDate = new SimpleDateFormat("dd/MM/yyyy").parse(request.getDueDate());

            Transaction transaction = new Transaction();
            transaction.setBorrowingDate(borrowingDate);
            transaction.setDueDate(dueDate);
            transaction.setBook(book.get());
            transaction.setUser(user.get());
            transactionRepository.save(transaction);

            Integer stockNow = book.get().getStock();
            if (stockNow-1 >= 0) {
                book.get().setStock(book.get().getStock()-1);
                bookRepository.save(book.get());
            } else {
                return false;
            }
        }
        return true;
    }

    // Fungsi untuk menambahkan data return date pada transaksi
    public Boolean bookReturn(Long id, TransactionRequest request) throws ParseException {
        Optional<Transaction> transaction = transactionRepository.findById(id);

        if (transaction.isPresent()) {
            Date returnDate = new SimpleDateFormat("dd/MM/yyyy").parse(request.getReturnDate());
            transaction.get().setReturnDate(returnDate);
            transactionRepository.save(transaction.get());
            Integer differentDate = transactionRepository.getDifferentDate(id);
            if (differentDate > 0) {
                Double totalPenalty = Double.valueOf(differentDate * 1000);
                transaction.get().setPenalty(totalPenalty);
                transactionRepository.save(transaction.get());
            }
        } else {
            return false;
        }
        return true;
    }

    public List<Object[]> getTop5MostBorrowedBooks() {
        return transactionRepository.findTop5MostBorrowedBooks();
    }

    public List<Object[]> getTop3MembersMostBorrowedBooksInMonth(int month) {
        return transactionRepository.findTop3MembersMostBorrowedBooksInMonth(month);
    }

    public List<Object[]> getTop3MembersMostLateReturns() {
        return transactionRepository.findTop3MembersMostLateReturns();
    }
}
