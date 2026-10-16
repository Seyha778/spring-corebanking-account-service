package com.apextech.accountservice.service.impl;

import com.apextech.accountservice.constant.AccountStatusEnum;
import com.apextech.accountservice.constant.CustomerKycStatusEnum;
import com.apextech.accountservice.dto.request.OpenAccountRequest;
import com.apextech.accountservice.dto.response.OpenAccountResponse;
import com.apextech.accountservice.dto.response.SessionData;
import com.apextech.accountservice.dto.response.SessionResponse;
import com.apextech.accountservice.entity.AccountSequenceEntity;
import com.apextech.accountservice.entity.BankAccountEntity;
import com.apextech.accountservice.entity.CustomerEntity;
import com.apextech.accountservice.repository.AccountSequenceRepository;
import com.apextech.accountservice.repository.BankAccountRepository;
import com.apextech.accountservice.repository.CustomerRepository;
import com.apextech.accountservice.service.AccountService;
import com.apextech.accountservice.service.SessionService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final CustomerRepository customerRepository;
    private final AccountSequenceRepository accountSequenceRepository;
    private final BankAccountRepository bankAccountRepository;
    private final SessionService sessionService;

    @Override
    public SessionResponse openAccountValidate(OpenAccountRequest request) {
        //Validate cif
        CustomerEntity customer = customerRepository
                .findByCif(request.getCif())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Customer CIF was not found."
                ));

        if (!CustomerKycStatusEnum.FULL.toString().equals(customer.getStatus())) {
            throw new IllegalArgumentException(
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
            throw new IllegalStateException(
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

        SessionData sessionData = SessionData.builder()
                .build();

        sessionData.put("accountNumber", accountNumber);
        sessionData.put("currency", request.getCurrency());
        sessionData.put("cif", cif);
        sessionData.put("branchCode", branchCode);
        sessionData.put("accountType", request.getAccountType());

        return sessionService.create(sessionData);

    }

    @Override
    public OpenAccountResponse openAccountCommit(String sessionId) {

        //Verify session id
        boolean isValidSessionId = sessionService.verify(sessionId);

        //Get session data
        if(isValidSessionId){
            SessionData sessionData = sessionService.getSession(sessionId);
            if (sessionData != null) {
                BankAccountEntity account = BankAccountEntity.builder()
                        .accountNumber((String) sessionData.get("accountNumber"))
                        .accountType((String) sessionData.get("accountType"))
                        .currency((String) sessionData.get("currency"))
                        .branchCode((String) sessionData.get("branchCode"))
                        .cif((String) sessionData.get("cif"))
                        .status(AccountStatusEnum.ACTIVE.toString())
                        .build();
                bankAccountRepository.save(account);
                return OpenAccountResponse.builder()
                        .accountNumber((String) sessionData.get("accountNumber"))
                        .accountType((String) sessionData.get("accountType"))
                        .currency((String) sessionData.get("currency"))
                        .build();
            }
        }
        return null;
    }
}
