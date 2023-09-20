package com.example.library.seeders;

import com.example.library.model.Transaction;
import com.example.library.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Component
public class TransactionSeeder {

    @Autowired
    private TransactionRepository transactionRepository;

    @PostConstruct
    @Transactional
    public void seedTransactions() throws ParseException {
        if (transactionRepository.findAll().isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            // Tanggal untuk transaksi 1
            Date today1 = new Date();
            Date dueDate1 = dateFormat.parse("15/10/2023");
            Date returnDate1 = dateFormat.parse("20/10/2023");

            // Tanggal untuk transaksi 2
            Date today2 = dateFormat.parse("10/09/2023");
            Date dueDate2 = dateFormat.parse("25/09/2023");
            Date returnDate2 = dateFormat.parse("28/09/2023");

            // Tanggal untuk transaksi 3
            Date today3 = dateFormat.parse("05/08/2023");
            Date dueDate3 = dateFormat.parse("20/08/2023");
            Date returnDate3 = dateFormat.parse("25/08/2023");

            // Tanggal untuk transaksi 4
            Date today4 = dateFormat.parse("01/07/2023");
            Date dueDate4 = dateFormat.parse("15/07/2023");
            Date returnDate4 = dateFormat.parse("18/07/2023");

            // Tanggal untuk transaksi 5
            Date today5 = dateFormat.parse("20/06/2023");
            Date dueDate5 = dateFormat.parse("05/07/2023");
            Date returnDate5 = dateFormat.parse("08/07/2023");

            Transaction transaction1 = new Transaction(today1, dueDate1, 1L, 1L);
            Transaction transaction2 = new Transaction(today2, dueDate2, 2L, 2L);
            Transaction transaction3 = new Transaction(today3, dueDate3, 3L, 3L);
            Transaction transaction4 = new Transaction(today4, dueDate4, 4L, 4L);
            Transaction transaction5 = new Transaction(today5, dueDate5, 5L, 5L);

            transactionRepository.saveAll(Arrays.asList(
                    transaction1, transaction2, transaction3, transaction4, transaction5
            ));
        }
    }
}