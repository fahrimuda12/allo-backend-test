package com.fahri_allobank.Frankfurter.exception;

import org.springframework.http.HttpStatus;

/**
 * Custom exception for bad request scenarios (400).
 * Use this when client sends invalid or malformed requests.
 */
public class CustomBadRequestException extends BaseCustomException {

    private static final String ERROR_CODE = "BAD_REQUEST";

    public CustomBadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST, ERROR_CODE);
    }

    public CustomBadRequestException(String message, Throwable cause) {
        super(message, cause, HttpStatus.BAD_REQUEST, ERROR_CODE);
    }

    public CustomBadRequestException(String field, String reason) {
        super(String.format("Invalid %s: %s", field, reason),
                HttpStatus.BAD_REQUEST, ERROR_CODE);
    }
}
