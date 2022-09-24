package com.banking.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class InvalidRequestException extends RuntimeException {
    private final HttpStatus status;
    private final String errorCode;
    private final List<String> errorMessages;

    public InvalidRequestException(HttpStatus status, String errorCode, String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessages = List.of(message);
    }

    public InvalidRequestException(String message) {
        this.status = HttpStatus.BAD_REQUEST;
        this.errorCode = ApiErrorResponse.VALIDATION_ERROR_MESSAGE;
        this.errorMessages = List.of(message);
    }

    public InvalidRequestException(HttpStatus status, String errorCode, List<String> messages) {
        this.errorMessages = messages;
        this.status = status;
        this.errorCode = errorCode;
    }
}
