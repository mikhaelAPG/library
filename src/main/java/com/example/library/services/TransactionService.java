package com.example.library.services;

import com.example.library.dto.request.TransactionRequest;
import com.example.library.dto.response.AllTransactionResponse;
import com.example.library.dto.response.TopBorrowedBookResponse;
import com.example.library.dto.response.TopLateReturnResponse;
import com.example.library.dto.response.TopMemberBorrowedBookResponse;
import com.example.library.model.Book;
import com.example.library.model.Transaction;
import com.example.library.model.User;
import com.example.library.repositories.BookRepository;
import com.example.library.repositories.TransactionRepository;
import com.example.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        if (request.getUserId() == null || request.getBookId() == null
                || isEmptyOrSpace(request.getBorrowingDate()) || isEmptyOrSpace(request.getDueDate())) {
            return false;
        }

        Optional<User> user = userRepository.findById(request.getUserId());
        Optional<Book> book = bookRepository.findById(request.getBookId());

        if (user.isPresent() && book.isPresent()) {
            Date borrowingDate = new SimpleDateFormat("dd/MM/yyyy").parse(request.getBorrowingDate());
            Date dueDate = new SimpleDateFormat("dd/MM/yyyy").parse(request.getDueDate());

            // Validasi dueDate harus setelah borrowingDate
            if (dueDate.before(borrowingDate)) {
                return false;
            }

            Transaction transaction = new Transaction();
            transaction.setBorrowingDate(borrowingDate);
            transaction.setDueDate(dueDate);
            transaction.setBook(book.get());
            transaction.setUser(user.get());
            transactionRepository.save(transaction);

            Integer stockNow = book.get().getStock();
            if (stockNow - 1 >= 0) {
                book.get().setStock(book.get().getStock() - 1);
                bookRepository.save(book.get());
            } else {
                return false;
            }
        }
        return true;
    }

    // Fungsi untuk menambahkan data return date pada transaksi
    public Boolean bookReturn(Long id, TransactionRequest request) throws ParseException {
        if (isEmptyOrSpace(request.getReturnDate())) {
            return false;
        }

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


    public List<TopBorrowedBookResponse> getTop5MostBorrowedBooks() {
        List<Object[]> top5BooksData = transactionRepository.findTop5MostBorrowedBooks();
        List<TopBorrowedBookResponse> top5Books = new ArrayList<>();

        for (Object[] data : top5BooksData) {
            String bookTitle = data[0].toString(); // Konversi dari BigInteger ke String
            Long totalBorrowedCount = ((BigInteger) data[1]).longValue(); // Konversi dari BigInteger ke Long
            top5Books.add(new TopBorrowedBookResponse(bookTitle, totalBorrowedCount));
        }

        return top5Books;
    }

    public List<TopMemberBorrowedBookResponse> getTop3MembersMostBorrowedBooksInMonth(int month) {
        List<Object[]> top3MembersData = transactionRepository.findTop3MembersMostBorrowedBooksInMonth(month);
        List<TopMemberBorrowedBookResponse> top3Members = new ArrayList<>();

        for (Object[] data : top3MembersData) {
            String userName = data[0].toString(); // Konversi dari BigInteger ke String
            Long totalBorrowedCount = ((BigInteger) data[1]).longValue(); // Konversi dari BigInteger ke Long
            top3Members.add(new TopMemberBorrowedBookResponse(userName, totalBorrowedCount));
        }

        return top3Members;
    }


    public List<TopLateReturnResponse> getTop3MembersMostLateReturnsDTO() {
        List<Object[]> top3MembersData = transactionRepository.findTop3MembersMostLateReturns();
        List<TopLateReturnResponse> top3Members = new ArrayList<>();

        for (Object[] data : top3MembersData) {
            String memberName = data[0].toString(); // Konversi dari BigInteger ke String
            Double lateReturnsCountDouble = (Double) data[1]; // Konversi dari BigInteger ke Double
            BigDecimal lateReturnsCount = BigDecimal.valueOf(lateReturnsCountDouble); // Konversi Double ke BigDecimal
            top3Members.add(new TopLateReturnResponse(memberName, lateReturnsCount.intValue())); // Konversi BigDecimal ke Integer
        }

        return top3Members;
    }

    public List<AllTransactionResponse> getAllTransactionsDTO() {
        List<Transaction> transactions = transactionRepository.findAllByReturnDateIsNotNullAndUserDeletedAtIsNotNullAndBookDeletedAtIsNotNull();
        List<AllTransactionResponse> transactionResponses = new ArrayList<>();

        for (Transaction transaction : transactions) {
            transactionResponses.add(new AllTransactionResponse(
                    transaction.getBorrowingDate(),
                    transaction.getDueDate(),
                    transaction.getReturnDate(),
                    transaction.getPenalty()
            ));
        }

        return transactionResponses;
    }

    public List<AllTransactionResponse> getReturnedTransactionsDTO() {
        List<Transaction> returnedTransactions = transactionRepository.findAllByReturnDateIsNotNullAndUserDeletedAtIsNotNullAndBookDeletedAtIsNotNull();
        List<AllTransactionResponse> returnedTransactionResponses = new ArrayList<>();

        for (Transaction transaction : returnedTransactions) {
            returnedTransactionResponses.add(new AllTransactionResponse(
                    transaction.getBorrowingDate(),
                    transaction.getDueDate(),
                    transaction.getReturnDate(),
                    transaction.getPenalty()
            ));
        }

        return returnedTransactionResponses;
    }

    // Fungsi untuk validasi apakah string null, kosong, atau hanya spasi
    private boolean isEmptyOrSpace(String str) {
        return str == null || str.trim().isEmpty();
    }
}
