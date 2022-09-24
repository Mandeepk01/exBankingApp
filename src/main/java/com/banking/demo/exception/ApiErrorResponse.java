package com.banking.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorResponse {

    public static final String VALIDATION_ERROR_MESSAGE = "validation_error";

    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ApiErrorResponse(final HttpStatus status, final String message, final String error) {
        this.status = status;
        this.message = message;
        this.errors = List.of(error);
    }
}
