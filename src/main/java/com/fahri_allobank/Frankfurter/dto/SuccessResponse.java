package com.fahri_allobank.Frankfurter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Standard success response wrapper for API responses.
 * Provides consistent response structure across all endpoints.
 * 
 * @param <T> The type of data being returned
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponse<T> {
    private int status;
    private String message;
    private T data;

    /**
     * Create a success response with default message
     */
    public static <T> SuccessResponse<T> of(T data) {
        return SuccessResponse.<T>builder()
                .status(200)
                .message("Success")
                .data(data)
                .build();
    }

    /**
     * Create a success response with custom message
     */
    public static <T> SuccessResponse<T> of(T data, String message) {
        return SuccessResponse.<T>builder()
                .status(200)
                .message(message)
                .data(data)
                .build();
    }

    /**
     * Create a success response with custom status and message
     */
    public static <T> SuccessResponse<T> of(int status, String message, T data) {
        return SuccessResponse.<T>builder()
                .status(status)
                .message(message)
                .data(data)
                .build();
    }
}
