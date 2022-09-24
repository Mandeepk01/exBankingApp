package com.banking.demo.controller;

import com.banking.demo.dto.CreateUserDto;
import com.banking.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@Validated
@Slf4j
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/create", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody @Valid CreateUserDto createUserDto){
        userService.createUser(createUserDto);
        return ResponseEntity.ok().build();
    }
}
