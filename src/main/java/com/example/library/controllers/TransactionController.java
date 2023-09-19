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
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity getTransactions() {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.transactionList());
    }

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
}
