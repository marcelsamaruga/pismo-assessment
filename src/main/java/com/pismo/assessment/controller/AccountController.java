package com.pismo.assessment.controller;

import com.pismo.assessment.dto.AccountDto;
import com.pismo.assessment.dto.AccountRequestDto;
import com.pismo.assessment.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService service;

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDto> getAccountDetails(@PathVariable final Long accountId) {
        return ResponseEntity
                .ok(service.getAccountDetails(accountId));
    }

    @PostMapping
    public ResponseEntity<AccountDto> saveAccount(@RequestBody final AccountRequestDto accountRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.save(accountRequestDto.getDocumentNumber()));
    }

}
