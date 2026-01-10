package com.fahri_allobank.Frankfurter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Standard error response DTO for API error handling.
 * Provides consistent error structure across all endpoints.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    
    /**

     */
    private int status;
    
    /**

     */
    private String message;
    
    /**

     */
    private String details;
}


    

    