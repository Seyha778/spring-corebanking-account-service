package com.apextech.accountservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "account_sequences",
        schema = "dgtl_backend",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_account_sequence",
                columnNames = {"cif", "branch_code"}
        )
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountSequenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cif", nullable = false, length = 10)
    private String cif;

    @Column(name = "branch_code", nullable = false)
    private String branchCode;

    @Column(name = "last_sequence", nullable = false)
    private Integer lastSequence;
}