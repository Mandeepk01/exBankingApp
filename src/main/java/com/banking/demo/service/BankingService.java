package com.banking.demo.service;

import com.banking.demo.dto.DepositRequestDto;
import com.banking.demo.dto.AccountDto;
import com.banking.demo.dto.WithdrawDto;
import com.banking.demo.entity.AccountMaster;
import com.banking.demo.entity.UserMaster;
import com.banking.demo.exception.InvalidRequestException;
import com.banking.demo.repository.UserMasterRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Validated
@Service
@RequiredArgsConstructor
public class BankingService {

    private final AccountMasterService accountMasterService;
    private final UserMasterRepository userMasterRepository;

    public AccountDto depositAmount(@NotNull DepositRequestDto depositRequestDto){
        UserMaster userMaster = userMasterRepository.findByUserIdIgnoreCase(depositRequestDto.getUserId());
        if(Objects.isNull(userMaster))
            throw new InvalidRequestException(String.format("%s user is not valid", depositRequestDto.getUserId()));
        final AccountMaster accountMaster = accountMasterService.depositAmount(depositRequestDto, userMaster.getIdKey());
        return AccountDto.builder()
                .balance(accountMaster.getBalance().doubleValue())
                .build();
    }

    public double getBalance(@NotBlank final String userId){
        final UserMaster userMaster = userMasterRepository.findByUserIdIgnoreCase(userId);
        if(Objects.isNull(userMaster))
            throw new InvalidRequestException(String.format("%s user is not valid", userId));
        return accountMasterService.getBalance(userMaster.getIdKey());
    }

    public AccountDto withdrawAmount(@NotNull WithdrawDto withdrawDto){
        final UserMaster userMaster = userMasterRepository.findByUserIdIgnoreCase(withdrawDto.getUserId());
        if(Objects.isNull(userMaster))
            throw new InvalidRequestException(String.format("%s user is not valid", withdrawDto.getUserId()));
        double remainingAmount = accountMasterService.withdrawAmount(userMaster.getIdKey(), withdrawDto.getWithdrawAmount());
        return AccountDto.builder()
                .balance(remainingAmount)
                .build();
    }
}
