package com.fahri_allobank.Frankfurter.exception;

import org.springframework.http.HttpStatus;

import static com.fahri_allobank.Frankfurter.config.Constants.ResponseCode.*;

/**
 * Custom exception for bad request scenarios (400).
 * Use this when client sends invalid or malformed requests.
 */
public class CustomBadRequestException extends BaseCustomException {

    public CustomBadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST, BAD_REQUEST);
    }

    public CustomBadRequestException(String message, Throwable cause) {
        super(message, cause, HttpStatus.BAD_REQUEST, BAD_REQUEST);
    }

    public CustomBadRequestException(String field, String reason) {
        super(String.format("Invalid %s: %s", field, reason),
                HttpStatus.BAD_REQUEST, BAD_REQUEST);
    }
}
