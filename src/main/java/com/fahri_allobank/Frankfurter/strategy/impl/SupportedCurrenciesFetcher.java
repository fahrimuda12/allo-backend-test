package com.fahri_allobank.Frankfurter.strategy.impl;

import com.fahri_allobank.Frankfurter.dto.SupportedCurrenciesResponse;
import com.fahri_allobank.Frankfurter.strategy.IDRDataFetcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

import static com.fahri_allobank.Frankfurter.config.Constants.*;

/**
 * Strategy implementation for fetching the list of supported currencies.
 */
@Component
public class SupportedCurrenciesFetcher implements IDRDataFetcher {

    private static final Logger logger = LoggerFactory.getLogger(SupportedCurrenciesFetcher.class);

    private final RestClient restClient;

    public SupportedCurrenciesFetcher(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public Object fetchData() {
        try {
            logger.info("Fetching supported currencies from Frankfurter API");

            @SuppressWarnings("unchecked")
            Map<String, String> currencies = restClient.get()
                    .uri("/currencies")
                    .retrieve()
                    .body(Map.class);

            SupportedCurrenciesResponse response = new SupportedCurrenciesResponse(currencies);

            logger.info("Successfully fetched {} supported currencies",
                    currencies != null ? currencies.size() : 0);
            return response;
        } catch (Exception e) {
            logger.error("Error fetching supported currencies", e);
            throw new RuntimeException("Failed to fetch supported currencies: " + e.getMessage(), e);
        }
    }

    @Override
    public String getResourceType() {
        return ResourceType.SUPPORTED_CURRENCIES;
    }
}
