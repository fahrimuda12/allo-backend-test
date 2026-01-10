package com.fahri_allobank.Frankfurter.exception;

import org.springframework.http.HttpStatus;

/**
 * Custom exception for data fetching failures from external APIs.
 * Extends BaseCustomException for consistent error handling.
 */
public class DataFetchException extends BaseCustomException {
    
    private static final String ERROR_CODE = "DATA_FETCH_ERROR";
    private final String source;
    
    public DataFetchException(String source, String message) {
        super(message, HttpStatus.SERVICE_UNAVAILABLE, ERROR_CODE);
        this.source = source;
    }
    
    public DataFetchException(String source, String message, Throwable cause) {
        super(message, cause, HttpStatus.SERVICE_UNAVAILABLE, ERROR_CODE);
        this.source = source;
    }
    
    public String getSource() {
        return source;
    }
}
