package com.fahri_allobank.Frankfurter.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fahri_allobank.Frankfurter.services.FinanceService;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the Finance Controller and Service layer.
 */
@SpringBootTest
class FinanceControllerIntegrationTest {

    @Autowired
    private FinanceService financeService;

    @Test
    void testGetLatestIDRRates() {
        Object data = financeService.getDataByResourceType("latest_idr_rates");

        assertNotNull(data);
        assertTrue(data.toString().contains("IDR"));
    }

    @Test
    void testGetHistoricalIDRUSD() {
        Object data = financeService.getDataByResourceType("historical_idr_usd");

        assertNotNull(data);
        assertTrue(data.toString().contains("IDR"));
    }

    @Test
    void testGetSupportedCurrencies() {
        Object data = financeService.getDataByResourceType("supported_currencies");

        assertNotNull(data);
    }

    @Test
    void testInvalidResourceType() {
        assertThrows(IllegalArgumentException.class, () -> {
            financeService.getDataByResourceType("invalid_resource");
        });
    }
}
