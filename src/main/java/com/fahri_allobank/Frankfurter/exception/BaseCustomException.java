package com.fahri_allobank.Frankfurter.exception;

import org.springframework.http.HttpStatus;

/**
 * Base custom exception class for all application exceptions.
 * Provides common functionality for HTTP status mapping and error details.
 */
public abstract class BaseCustomException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String errorCode;

    protected BaseCustomException(String message, HttpStatus httpStatus, String errorCode) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }

    protected BaseCustomException(String message, Throwable cause, HttpStatus httpStatus, String errorCode) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
