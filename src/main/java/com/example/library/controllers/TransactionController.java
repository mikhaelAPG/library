package com.example.library.controllers;

import com.example.library.dto.TransactionRequest;
import com.example.library.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("")
    public ResponseEntity addTransaction(@RequestBody TransactionRequest request) throws ParseException {
        if (transactionService.addTransaction(request)) {
            return ResponseEntity.status(HttpStatus.OK).body("Transaction Added Successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to Add Transaction.");
        }
    }

    @PostMapping("/return/{id}")
    public ResponseEntity bookReturn(@PathVariable Long id, @RequestBody TransactionRequest request) throws ParseException {
        if (transactionService.bookReturn(id, request)) {
            return ResponseEntity.status(HttpStatus.OK).body("Transaction Added Successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to Add Transaction.");
        }
    }

    @GetMapping("/top5-most-borrowed")
    public List<Object[]> getTop5MostBorrowedBooks() {
        List<Object[]> top5Books = transactionService.getTop5MostBorrowedBooks();
        return top5Books;
    }

    @GetMapping("/top3-most-borrowed")
    public List<Object[]> getTop3MembersMostBorrowedBooksInMonth(@RequestParam("month") int month) {
        List<Object[]> top3MembersData = transactionService.getTop3MembersMostBorrowedBooksInMonth(month);
        return top3MembersData;
    }

    @GetMapping("/top3-most-late-returns")
    public List<Object[]> getTop3MembersMostLateReturns() {
        List<Object[]> top3MembersData = transactionService.getTop3MembersMostLateReturns();
        return top3MembersData;
    }
}
