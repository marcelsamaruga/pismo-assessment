package com.pismo.assessment.service;

import com.pismo.assessment.dto.AccountDto;
import com.pismo.assessment.entity.Account;
import com.pismo.assessment.exception.AccountNotFoundException;
import com.pismo.assessment.repository.AccountRepository;
import com.pismo.assessment.util.Converter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository repository;

    @Transactional(readOnly = true)
    public AccountDto getAccountDetails(Long accountId) {
        var account = repository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account " + accountId + " not found."));

        return Converter.INSTANCE.toAccountDto(account);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public AccountDto save(String documentNumber) {
        var account = repository.save(
                Account.builder().documentNumber(documentNumber).build()
        );

        return Converter.INSTANCE.toAccountDto(account);
    }
}
