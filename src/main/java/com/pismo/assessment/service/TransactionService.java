package com.pismo.assessment.service;

import com.pismo.assessment.dto.TransactionDto;
import com.pismo.assessment.entity.OperationTypeEnum;
import com.pismo.assessment.entity.Transaction;
import com.pismo.assessment.exception.AccountNotFoundException;
import com.pismo.assessment.repository.AccountRepository;
import com.pismo.assessment.repository.TransactionRepository;
import com.pismo.assessment.util.Converter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public TransactionDto save(TransactionDto transactionDto) {
        var account = accountRepository.findById(transactionDto.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Account " + transactionDto.getAccountId() + " not found"));

        var operationType = OperationTypeEnum.findById(transactionDto.getOperationTypeId());

        var transaction = transactionRepository.save(
                Transaction.builder()
                    .account(account)
                    .operationType(operationType)
                    .amount(operationType.getAmountRule().apply(transactionDto.getAmount()))
                    .build()
        );

        return Converter.INSTANCE.toTransactionDto(transaction);
    }
}
