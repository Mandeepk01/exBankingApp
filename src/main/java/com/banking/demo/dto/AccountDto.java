package com.banking.demo.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@Getter
@Setter
@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public class AccountDto {

    private Double balance;
}
