package com.fahri_allobank.Frankfurter.strategy.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.fahri_allobank.Frankfurter.dto.LatestRatesResponse;
import com.fahri_allobank.Frankfurter.strategy.IDRDataFetcher;
import com.fahri_allobank.Frankfurter.util.SpreadCalculator;

import static com.fahri_allobank.Frankfurter.config.Constants.*;

/**
 * Strategy implementation for fetching the latest IDR exchange rates.
 * This strategy also calculates and adds the USD_BuySpread_IDR field.
 */
@Component
public class LatestIDRRatesFetcher implements IDRDataFetcher {

    private static final Logger logger = LoggerFactory.getLogger(LatestIDRRatesFetcher.class);

    private final RestClient restClient;
    private final SpreadCalculator spreadCalculator;

    public LatestIDRRatesFetcher(RestClient restClient, SpreadCalculator spreadCalculator) {
        this.restClient = restClient;
        this.spreadCalculator = spreadCalculator;
    }

    @Override
    public Object fetchData() {
        try {
            logger.info("Fetching latest IDR rates from Frankfurter API");

            LatestRatesResponse response = restClient.get()
                    .uri("/latest?base=IDR")
                    .retrieve()
                    .body(LatestRatesResponse.class);

            if (response != null && response.getRates() != null && response.getRates().containsKey(USD)) {
                double usdRate = response.getRates().get(USD);
                double usdBuySpread = spreadCalculator.calculateUsdBuySpread(usdRate);
                response.setUsdBuySpreadIdr(usdBuySpread);

                logger.info("Successfully fetched latest IDR rates. USD rate: {}, USD_BuySpread_IDR: {}",
                        usdRate, usdBuySpread);
            }

            return response;
        } catch (Exception e) {
            logger.error("Error fetching latest IDR rates", e);
            throw new RuntimeException("Failed to fetch latest IDR rates: " + e.getMessage(), e);
        }
    }

    @Override
    public String getResourceType() {
        return ResourceType.LATEST_IDR_RATES;
    }
}
