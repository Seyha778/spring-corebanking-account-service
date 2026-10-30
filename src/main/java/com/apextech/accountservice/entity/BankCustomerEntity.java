package com.apextech.accountservice.entity;

import com.apextech.accountservice.constant.CustomerKycStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(
        name = "bank_customers",
        schema = "dgtl_backend",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_bank_customer_cif",
                        columnNames = "cif"
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankCustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "cif",
            nullable = false,
            unique = true,
            insertable = false,
            updatable = false,
            length = 10
    )
    private String cif;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nationality", nullable = false)
    private String nationality;

    @Column(name = "document_type", nullable = false)
    private String documentType;

    @Column(name = "document_number", nullable = false)
    private String documentNumber;

    @Column(name = "branch_code", nullable = false)
    private String branchCode;

    @Column(name = "status", nullable = false)
    @Builder.Default
    private String status = CustomerKycStatusEnum.HALF.toString();

    @CreationTimestamp
    @Column(name = "created_on", nullable = false, updatable = false)
    private OffsetDateTime createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @UpdateTimestamp
    @Column(name = "modified_on")
    private OffsetDateTime modifiedOn;

    @Column(name = "modified_by")
    private String modifiedBy;
}
