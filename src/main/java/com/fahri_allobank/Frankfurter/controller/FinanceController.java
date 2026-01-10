package com.fahri_allobank.Frankfurter.controller;

import com.fahri_allobank.Frankfurter.dto.SuccessResponse;
import com.fahri_allobank.Frankfurter.services.FinanceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
     * @return ResponseEntity containing the requested data wrapped in
     *         SuccessResponse
     */
    @GetMapping("/data/{resourceType}")
    public ResponseEntity<SuccessResponse<Object>> getFinanceData(@PathVariable String resourceType) {
        logger.info("Received request for resource type: {}", resourceType);

        Object data = financeService.getDataByResourceType(resourceType);
        logger.info("Successfully retrieved data for resource type: {}", resourceType);

        return ResponseEntity.ok(SuccessResponse.of(data, "Data retrieved successfully"));
    }
}
