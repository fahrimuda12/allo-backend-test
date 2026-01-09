package com.fahri_allobank.Frankfurter.controller;

import com.fahri_allobank.Frankfurter.services.FinanceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for finance data endpoints.
 */
@RestController
@RequestMapping("/api/finance")
public class FinanceController {

    private static final Logger logger = LoggerFactory.getLogger(FinanceController.class);

    private final FinanceService financeService;

    public FinanceController(FinanceService financeService) {
        this.financeService = financeService;
    }

    /**
     * Polymorphic endpoint that serves different resource types.
     * Supported resource types:
     * - latest_idr_rates: Latest exchange rates with IDR as base
     * - historical_idr_usd: Historical IDR to USD rates
     * - supported_currencies: List of all supported currencies
     * 
     * @param resourceType The type of resource to retrieve
     * @return ResponseEntity containing the requested data
     */
    @GetMapping("/data/{resourceType}")
    public ResponseEntity<Object> getFinanceData(@PathVariable String resourceType) {
        logger.info("Received request for resource type: {}", resourceType);

        try {
            Object data = financeService.getDataByResourceType(resourceType);
            logger.info("Successfully retrieved data for resource type: {}", resourceType);
            return ResponseEntity.ok(data);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid resource type requested: {}", resourceType);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Invalid resource type: " + resourceType));
        } catch (Exception e) {
            logger.error("Error retrieving data for resource type: {}", resourceType, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error: " + e.getMessage()));
        }
    }

    /**
     * Simple error response DTO
     */
    private record ErrorResponse(String error) {
    }
}
