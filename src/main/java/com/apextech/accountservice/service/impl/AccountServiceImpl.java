package com.apextech.accountservice.service.impl;

import com.apextech.accountservice.constant.AccountStatusEnum;
import com.apextech.accountservice.constant.CustomerKycStatusEnum;
import com.apextech.accountservice.dto.request.OpenAccountRequest;
import com.apextech.accountservice.dto.request.SessionRequest;
import com.apextech.accountservice.dto.response.OpenAccountResponse;
import com.apextech.accountservice.dto.response.OpenAccountSessionResponse;
import com.apextech.accountservice.dto.response.SessionData;
import com.apextech.accountservice.dto.response.SessionResponse;
import com.apextech.accountservice.entity.AccountSequenceEntity;
import com.apextech.accountservice.entity.BankAccountEntity;
import com.apextech.accountservice.entity.BankCustomerEntity;
import com.apextech.accountservice.repository.AccountSequenceRepository;
import com.apextech.accountservice.repository.BankAccountRepository;
import com.apextech.accountservice.repository.CustomerRepository;
import com.apextech.accountservice.service.AccountService;
import com.apextech.accountservice.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final CustomerRepository customerRepository;
    private final AccountSequenceRepository accountSequenceRepository;
    private final BankAccountRepository bankAccountRepository;
    private final SessionService sessionService;

    private final ObjectMapper objectMapper;

    @Override
    public SessionResponse openAccountValidate(OpenAccountRequest request) {
        //Validate cif
        BankCustomerEntity customer = customerRepository
                .findByCif(request.getCif())
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                        "Customer CIF was not found."
                ));

        if (!CustomerKycStatusEnum.FULL.toString().equals(customer.getStatus())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Customer must full KYC before opening an account."
            );
        }

        String branchCode = request.getBranchCode();
        String cif = customer.getCif();

        int seqNo = accountSequenceRepository
                .findTopByCifOrderByLastSequenceDesc(cif)
                .map(accountSequence -> accountSequence.getLastSequence() + 1)
                .orElse(1);

        if (seqNo > 999) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Account sequence limit reached for this CIF and branch."
            );
        }

        AccountSequenceEntity sequence = new AccountSequenceEntity();
        sequence.setCif(cif);
        sequence.setBranchCode(branchCode);
        sequence.setLastSequence(seqNo);
        accountSequenceRepository.save(sequence);

        String accountNumber = branchCode
                + cif
                + String.format("%03d", seqNo);

        // build account opening date
        OpenAccountSessionResponse data = OpenAccountSessionResponse.builder()
                .accountNumber(accountNumber)
                .accountType(request.getAccountType())
                .currency(request.getCurrency())
                .cif(cif)
                .branchCode(branchCode)
                .build();

        SessionData sessionData = SessionData.builder()
                .build();

        sessionData.put("data", data);
        return sessionService.create(sessionData);

    }

    @Override
    public OpenAccountResponse openAccountCommit(SessionRequest request) {

        //Get session data
        SessionData sessionData = sessionService.getSession(request.getSessionId());
        if (sessionData != null) {
            OpenAccountSessionResponse openingAccountSession = objectMapper.convertValue(
                    sessionData.get("data"),
                    OpenAccountSessionResponse.class
            );
            String accountNo = openingAccountSession.getAccountNumber();
            String accountType = openingAccountSession.getAccountType();
            String currency = openingAccountSession.getCurrency();

            BankAccountEntity account = BankAccountEntity.builder()
                    .accountNumber(accountNo)
                    .accountType(accountType)
                    .currency(currency)
                    .branchCode(openingAccountSession.getBranchCode())
                    .cif(openingAccountSession.getCif())
                    .status(AccountStatusEnum.ACTIVE.toString())
                    .build();
            bankAccountRepository.save(account);
            return OpenAccountResponse.builder()
                    .accountNumber(accountNo)
                    .accountType(accountType)
                    .currency(currency)
                    .build();
        }
        return null;
    }
}
