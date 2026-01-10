package com.fahri_allobank.Frankfurter.strategy;

import com.fahri_allobank.Frankfurter.dto.LatestRatesResponse;
import com.fahri_allobank.Frankfurter.strategy.impl.LatestIDRRatesFetcher;
import com.fahri_allobank.Frankfurter.util.SpreadCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LatestIDRRatesFetcherTest {

    @Mock
    private RestClient restClient;

    @Mock
    private RestClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private RestClient.ResponseSpec responseSpec;

    private SpreadCalculator spreadCalculator;
    private LatestIDRRatesFetcher fetcher;

    @BeforeEach
    void setUp() {
        spreadCalculator = new SpreadCalculator("test-user");
        fetcher = new LatestIDRRatesFetcher(restClient, spreadCalculator);
    }

    @Test
    void testFetchDataSuccess() {
        // Prepare mock response
        LatestRatesResponse mockResponse = new LatestRatesResponse();
        mockResponse.setAmount(1.0);
        mockResponse.setBase("IDR");
        mockResponse.setDate("2024-01-09");
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 0.000063);
        rates.put("EUR", 0.000058);
        mockResponse.setRates(rates);

        // Setup mock behavior
        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(LatestRatesResponse.class)).thenReturn(mockResponse);

        // Execute
        Object result = fetcher.fetchData();

        // Verify
        assertNotNull(result);
        assertTrue(result instanceof LatestRatesResponse);
        LatestRatesResponse response = (LatestRatesResponse) result;
        assertNotNull(response.getUsdBuySpreadIdr());
        assertTrue(response.getUsdBuySpreadIdr() > 0);

        verify(restClient, times(1)).get();
    }

    @Test
    void testGetResourceType() {
        assertEquals("latest_idr_rates", fetcher.getResourceType());
    }

    @Test
    void testUsdBuySpreadCalculation() {
        // Prepare mock response
        LatestRatesResponse mockResponse = new LatestRatesResponse();
        Map<String, Double> rates = new HashMap<>();
        double usdRate = 0.000063;
        rates.put("USD", usdRate);
        mockResponse.setRates(rates);

        // Setup mock behavior
        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(LatestRatesResponse.class)).thenReturn(mockResponse);

        // Execute
        LatestRatesResponse result = (LatestRatesResponse) fetcher.fetchData();

        // Verify the spread calculation
        double expectedSpread = spreadCalculator.calculateUsdBuySpread(usdRate);
        assertEquals(expectedSpread, result.getUsdBuySpreadIdr(), 0.01);
    }
}
