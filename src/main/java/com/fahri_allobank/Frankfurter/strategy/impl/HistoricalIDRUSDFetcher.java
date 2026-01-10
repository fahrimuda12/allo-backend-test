package com.fahri_allobank.Frankfurter.strategy.impl;

import com.fahri_allobank.Frankfurter.dto.HistoricalRatesResponse;
import com.fahri_allobank.Frankfurter.strategy.IDRDataFetcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static com.fahri_allobank.Frankfurter.config.Constants.*;

/**
 * Strategy implementation for fetching historical IDR to USD exchange rates.
 */
@Component
public class HistoricalIDRUSDFetcher implements IDRDataFetcher {

    private static final Logger logger = LoggerFactory.getLogger(HistoricalIDRUSDFetcher.class);

    private final RestClient restClient;

    public HistoricalIDRUSDFetcher(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public Object fetchData() {
        try {
            logger.info("Fetching historical IDR to USD rates from Frankfurter API");

            HistoricalRatesResponse response = restClient.get()
                    .uri("/2024-01-01..2024-01-05?from=IDR&to=USD")
                    .retrieve()
                    .body(HistoricalRatesResponse.class);

            logger.info("Successfully fetched historical IDR to USD rates");
            return response;
        } catch (Exception e) {
            logger.error("Error fetching historical IDR to USD rates", e);
            throw new RuntimeException("Failed to fetch historical IDR to USD rates: " + e.getMessage(), e);
        }
    }

    @Override
    public String getResourceType() {
        return ResourceType.HISTORICAL_IDR_USD;
    }
}
