package com.auditing.repository;

import com.auditing.model.Account;
import com.auditing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findAccountByAccountNumber(String accountNumber);
    Account findAccountByUser(User user);
    boolean existsByAccountNumber(String accountNumber);
}
