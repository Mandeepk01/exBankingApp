package com.banking.demo.controller;

import com.banking.demo.dto.DepositRequestDto;
import com.banking.demo.dto.AccountDto;
import com.banking.demo.dto.WithdrawDto;
import com.banking.demo.service.BankingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@Validated
@Slf4j
@RequestMapping(value = "/banking")
public class BankingController {

    private final BankingService bankingService;

    @PostMapping(value = "/deposit", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDto> deposit(@RequestBody @Valid DepositRequestDto depositRequestDto){
        AccountDto accountDto = bankingService.depositAmount(depositRequestDto);
        return ResponseEntity.ok(accountDto);
    }

    @PostMapping(value = "/withdraw", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDto> withdraw(@RequestBody @Valid WithdrawDto withdrawDto){
        AccountDto accountDto = bankingService.withdrawAmount(withdrawDto);
        return ResponseEntity.ok(accountDto);
    }

    @GetMapping(value = "/balance/{userId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDto> checkBalance(@NotBlank @PathVariable final String userId){
        double balance = bankingService.getBalance(userId);
        return ResponseEntity.ok(AccountDto.builder()
                        .balance(balance)
                        .build());
    }
}
