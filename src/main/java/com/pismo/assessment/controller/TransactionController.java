package com.pismo.assessment.controller;

import com.pismo.assessment.dto.TransactionDto;
import com.pismo.assessment.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService service;

    @PostMapping
    public ResponseEntity<TransactionDto> saveTransaction(@RequestBody final TransactionDto transactionDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.save(transactionDto));
    }

}
