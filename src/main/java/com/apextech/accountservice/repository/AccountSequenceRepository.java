package com.apextech.accountservice.repository;

import com.apextech.accountservice.entity.AccountSequenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountSequenceRepository extends JpaRepository<AccountSequenceEntity, Long> {
    Optional<AccountSequenceEntity> findTopByCifOrderByLastSequenceDesc(String cif);
}