package com.fahri_allobank.Frankfurter.exception;

import org.springframework.http.HttpStatus;

import static com.fahri_allobank.Frankfurter.config.Constants.ResponseCode.*;

/**
 * Custom exception for data fetching failures from external APIs.
 * Extends BaseCustomException for consistent error handling.
 */
public class DataFetchException extends BaseCustomException {
    
    private final String source;
    
    public DataFetchException(String source, String message) {
        super(message, HttpStatus.SERVICE_UNAVAILABLE, DATA_FETCH_ERROR);
        this.source = source;
    }
    
    public DataFetchException(String source, String message, Throwable cause) {
        super(message, cause, HttpStatus.SERVICE_UNAVAILABLE, DATA_FETCH_ERROR);
        this.source = source;
    }
    
    public String getSource() {
        return source;
    }
}
