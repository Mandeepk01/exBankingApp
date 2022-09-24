package com.banking.demo.service;

import com.banking.demo.dto.DepositRequestDto;
import com.banking.demo.entity.AccountMaster;
import com.banking.demo.exception.InvalidRequestException;
import com.banking.demo.repository.AccountMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountMasterServiceImpl implements AccountMasterService{

    private final AccountMasterRepository accountMasterRepository;

    @Override
    public AccountMaster depositAmount(DepositRequestDto depositRequestDto, long userIdKey){
        AccountMaster accountMaster = accountMasterRepository.findByUserMasterIdKey(userIdKey);
        double balance = accountMaster.getBalance().doubleValue() + depositRequestDto.getAmount().doubleValue();
        accountMaster.setBalance(new BigDecimal(balance));
        return accountMasterRepository.saveAndFlush(accountMaster);
    }

    @Override
    public double getBalance(long userIdKey) {
        AccountMaster accountMaster = accountMasterRepository.findByUserMasterIdKey(userIdKey);
        return accountMaster.getBalance().doubleValue();
    }

    @Override
    public AccountMaster createAccount(final long userIdKey, final String currency) {
        AccountMaster accountMaster = AccountMaster.builder()
                .balance(new BigDecimal("0.0"))
                .userMasterIdKey(userIdKey)
                .currency(currency)
                .build();
        return accountMasterRepository.saveAndFlush(accountMaster);
    }

    @Override
    public double withdrawAmount(long userIdKey, double amount) {
        AccountMaster accountMaster = accountMasterRepository.findByUserMasterIdKey(userIdKey);
        if(amount > accountMaster.getBalance().doubleValue())
            throw new InvalidRequestException("withdraw amount exceeding total balance :"+accountMaster.getBalance()+" in account");
        final double remainingBalance = accountMaster.getBalance().doubleValue() - amount ;
        accountMaster.setBalance(new BigDecimal(remainingBalance));
        accountMasterRepository.saveAndFlush(accountMaster);
        return remainingBalance;
    }
}
