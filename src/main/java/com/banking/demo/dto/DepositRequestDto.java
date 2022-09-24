package com.banking.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
public class DepositRequestDto {

    @Positive
    private BigDecimal amount;
    @NotBlank
    private String userId;
}
