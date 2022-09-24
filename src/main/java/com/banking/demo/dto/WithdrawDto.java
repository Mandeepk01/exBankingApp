package com.banking.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Setter
@ToString
@Builder
public class WithdrawDto {

    @NotBlank
    private String userId;
    @Positive
    @Min(1)
    private double withdrawAmount;
}
