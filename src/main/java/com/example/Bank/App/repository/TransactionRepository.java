package com.example.Bank.App.repository;

import com.example.Bank.App.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountNumberOrderByDateDesc(long accountNumber);
}
