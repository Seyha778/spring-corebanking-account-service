package com.apextech.accountservice.repository;

import com.apextech.accountservice.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository
        extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByCif(String cif);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByDocumentTypeAndDocumentNumber(
            String documentType,
            String documentNumber
    );
}