package com.example.Bank.App.repository;

import com.example.Bank.App.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
