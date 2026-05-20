package com.order.service.exception;

import com.order.service.dto.CommonApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public String handleCustom(CustomException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<CommonApiResponse> handlePaymentException(PaymentException ex) {

        log.error("Payment exception occurred: {}", ex.getMessage());

        CommonApiResponse response =
                CommonApiResponse.builder()
                        .success(false)
                        .code(ex.getErrorCode())
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity
                .status(ex.getStatusCode())
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonApiResponse> handleValidationException(MethodArgumentNotValidException ex) {

        String errorMessage =
                ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(error ->
                                error.getField()
                                        + ": "
                                        + error.getDefaultMessage())
                        .collect(Collectors.joining(", "));

        CommonApiResponse response =
                CommonApiResponse.builder()
                        .success(false)
                        .code("VALIDATION_ERROR")
                        .message(errorMessage)
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonApiResponse> handleGenericException(Exception ex) {

        log.error("Unexpected exception occurred", ex);
        CommonApiResponse response =
                CommonApiResponse.builder()
                        .success(false)
                        .code("INTERNAL_SERVER_ERROR")
                        .message("Something went wrong")
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<CommonApiResponse> handleNotFound(NoResourceFoundException ex) {

        CommonApiResponse response =
                CommonApiResponse.builder()
                        .success(false)
                        .code("RESOURCE_NOT_FOUND")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

}
