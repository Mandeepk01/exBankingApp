package com.banking.demo.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
public class CreateUserDto {

    @NotBlank(message = "UserId is mandatory to pass")
    private String userId;
    @NotBlank(message = "first name is mandatory to pass")
    private String firstName;
    @NotBlank(message = "last name is mandatory to pass")
    private String lastName;
    @NotBlank(message = "mobile no is mandatory to pass")
    private String mobileNo;
    @NotBlank(message = "currency is mandatory to pass")
    private String currency;
}
