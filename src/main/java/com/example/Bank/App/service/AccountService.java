package com.example.Bank.App.service;

import com.example.Bank.App.model.Account;
import com.example.Bank.App.model.Transaction;
import com.example.Bank.App.model.TransactionType;
import com.example.Bank.App.repository.AccountRepository;
import com.example.Bank.App.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    private void saveTransaction(long accountNumber, double amount, double balanceAfter, TransactionType type) {
        Transaction transaction = new Transaction();
        transaction.setAccountNumber(accountNumber);
        transaction.setAmount(amount);
        transaction.setBalanceAfterTransaction(balanceAfter);
        transaction.setDate(LocalDate.now());
        transaction.setType(type);
        transactionRepository.save(transaction);
    }

    // Open Account
    private static long generateAccountNumber() {
        return ThreadLocalRandom.current().nextLong(1_000_000_000L, 10_000_000_000L);
    }

    public void openAccount(String accountHolderName, double balance) {
        Account account = new Account();
        account.setAccountNumber(generateAccountNumber());
        account.setAccountHolderName(accountHolderName);
        account.setBalance(balance);
        accountRepository.save(account);

        saveTransaction(account.getAccountNumber(), balance, balance, TransactionType.DEPOSIT);
    }

    // Withdraw
    public String withdrawMoney(long accountNumber, double amount) {
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (amount <= 0) {
            return "Withdrawal amount must be greater than zero";
        }

        if (account.getBalance() < amount) {
            return "Insufficient balance";
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);

        saveTransaction(accountNumber, amount, account.getBalance(), TransactionType.WITHDRAW);

        return "Withdrawal successful. Remaining balance: " + account.getBalance();
    }

    //Deposit
    public String deposit(long accountNumber, double amount) {
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero");
        }

        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        saveTransaction(accountNumber, amount, account.getBalance(), TransactionType.DEPOSIT);
        return "Deposit successful. New balance: " + account.getBalance();
    }

    // Show balance
    public Account showBalance(long accountNumber) {
        return accountRepository.findById(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found for number: " + accountNumber));
    }

    // Statement
    public List<Transaction> getStatement(long accountNumber) {
        accountRepository.findById(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found for number: " + accountNumber));

        return transactionRepository.findByAccountNumberOrderByDateDesc(accountNumber);
    }

    // Bank transfer
    public void transferMoney(long fromAccount, long toAccount, double amount) {
        if (fromAccount == toAccount) {
            throw new RuntimeException("Sender and receiver accounts cannot be the same.");
        }

        Account sender = accountRepository.findById(fromAccount)
                .orElseThrow(() -> new RuntimeException("Sender account not found."));
        Account receiver = accountRepository.findById(toAccount)
                .orElseThrow(() -> new RuntimeException("Receiver account not found."));

        if (sender.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance in sender's account.");
        }

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        accountRepository.save(sender);
        accountRepository.save(receiver);

        saveTransaction(sender.getAccountNumber(), amount, sender.getBalance(), TransactionType.TRANSFER);
        saveTransaction(receiver.getAccountNumber(), amount, receiver.getBalance(), TransactionType.TRANSFER);

    }

}
