package com.banking.demo.service;

import com.banking.demo.dto.CreateUserDto;
import com.banking.demo.entity.UserMaster;
import com.banking.demo.exception.InvalidRequestException;
import com.banking.demo.repository.UserMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@Service
@Validated
public class UserService {

    private final UserMasterRepository userMasterRepository;
    public final AccountMasterService accountMasterService;

    @Transactional(rollbackFor = Exception.class)
    public void createUser(@NotNull final CreateUserDto createUserDto){
        if(userMasterRepository.existsByUserIdIgnoreCase(createUserDto.getUserId()))
            throw new InvalidRequestException(String.format("%s user already exists", createUserDto.getUserId()));
        UserMaster userMaster = UserMaster.builder()
                .firstName(createUserDto.getFirstName())
                .userId(createUserDto.getUserId())
                .lastName(createUserDto.getLastName())
                .mobileNumber(createUserDto.getMobileNo())
                .build();
        UserMaster insertedUserMaster = userMasterRepository.saveAndFlush(userMaster);
        accountMasterService.createAccount(insertedUserMaster.getIdKey(), createUserDto.getCurrency());
    }
}
