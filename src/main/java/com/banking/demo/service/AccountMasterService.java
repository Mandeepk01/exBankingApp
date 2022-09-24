package com.banking.demo.service;

import com.banking.demo.dto.DepositRequestDto;
import com.banking.demo.entity.AccountMaster;

public interface AccountMasterService {

    AccountMaster depositAmount(DepositRequestDto depositRequestDto, long userIdKey);
    double getBalance(long userIdKey);
    AccountMaster createAccount(final long userIdKey, final String currency);
    double withdrawAmount(final long userIdKey, final double amount);
}
