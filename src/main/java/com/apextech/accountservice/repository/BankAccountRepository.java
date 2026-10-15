package com.apextech.accountservice.repository;

import com.apextech.accountservice.entity.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccountEntity, Long> {

    boolean existsByAccountNumber(String accountNumber);
}