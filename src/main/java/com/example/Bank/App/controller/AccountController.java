package com.example.Bank.App.controller;

import com.example.Bank.App.model.Account;
import com.example.Bank.App.model.Transaction;
import com.example.Bank.App.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping("/{accountNumber}/statement")
    public List<Transaction> getStatement(@PathVariable long accountNumber) {
        return accountService.getStatement(accountNumber);
    }

    @PostMapping("/open")
    public void openAccount(@RequestBody Account account){
        accountService.openAccount(account.getAccountHolderName(), account.getBalance());
    }

    @PostMapping("/{accountNumber}/withdraw/{amount}")
    public String withdraw(@PathVariable long accountNumber, @PathVariable double amount) {
        return accountService.withdrawMoney(accountNumber, amount);
    }

    @PostMapping("/{accountNumber}/deposit")
    public String deposit(
            @PathVariable long accountNumber,
            @RequestParam double amount
    ) {
        return accountService.deposit(accountNumber, amount);
    }

    @GetMapping("/balance/{accountNumber}")
    public Account getBalance(@PathVariable long accountNumber) {
        return accountService.showBalance(accountNumber);
    }

    @PostMapping("/transfer")
    public String transferMoney(
            @RequestParam long fromAccount,
            @RequestParam long toAccount,
            @RequestParam double amount) {
        accountService.transferMoney(fromAccount, toAccount, amount);
        return "Transfer of " + amount + " from " + fromAccount + " to " + toAccount + " successful.";
    }

}
