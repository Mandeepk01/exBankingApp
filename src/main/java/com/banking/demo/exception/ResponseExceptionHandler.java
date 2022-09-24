package com.banking.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class ResponseExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex) {
        log.warn(ex.getClass().getName());
        final List<String> errors = getValidationErrors(ex.getBindingResult());
        final ApiErrorResponse apiErrorResponse =
                new ApiErrorResponse(
                        HttpStatus.BAD_REQUEST,
                        ApiErrorResponse.VALIDATION_ERROR_MESSAGE,
                        errors);
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        log.warn(ex.getClass().getName());
        final List<String> errors = new ArrayList<>();
        for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getMessage());
        }
        final ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST, ApiErrorResponse.VALIDATION_ERROR_MESSAGE, errors);
        return new ResponseEntity<>(apiErrorResponse, new HttpHeaders(), apiErrorResponse.getStatus());
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidRequestException(final InvalidRequestException ex) {
        log.warn(ex.getClass().getName());
        final ApiErrorResponse apiErrorResponse =
                new ApiErrorResponse(
                        ex.getStatus(),
                        ex.getErrorCode(),
                        ex.getErrorMessages());
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getStatus());
    }

    private List<String> getValidationErrors(BindingResult bindingResult) {
        final List<String> errors = new ArrayList<>();
        for (final FieldError error : bindingResult.getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : bindingResult.getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        return errors;
    }
}
