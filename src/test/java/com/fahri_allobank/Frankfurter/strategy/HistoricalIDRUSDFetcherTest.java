package com.fahri_allobank.Frankfurter.strategy;

import com.fahri_allobank.Frankfurter.dto.HistoricalRatesResponse;
import com.fahri_allobank.Frankfurter.strategy.impl.HistoricalIDRUSDFetcher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HistoricalIDRUSDFetcherTest {

    @Mock
    private RestClient restClient;

    @Mock
    private RestClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private RestClient.ResponseSpec responseSpec;

    private HistoricalIDRUSDFetcher fetcher;

    @BeforeEach
    void setUp() {
        fetcher = new HistoricalIDRUSDFetcher(restClient);
    }

    @Test
    void testFetchDataSuccess() {
        // Prepare mock response
        HistoricalRatesResponse mockResponse = new HistoricalRatesResponse();
        mockResponse.setAmount(1.0);
        mockResponse.setBase("IDR");
        mockResponse.setStart_date("2024-01-01");
        mockResponse.setEnd_date("2024-01-05");

        Map<String, Map<String, Double>> rates = new HashMap<>();
        Map<String, Double> day1Rates = new HashMap<>();
        day1Rates.put("USD", 0.000063);
        rates.put("2024-01-01", day1Rates);
        mockResponse.setRates(rates);

        // Setup mock behavior
        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(HistoricalRatesResponse.class)).thenReturn(mockResponse);

        // Execute
        Object result = fetcher.fetchData();

        // Verify
        assertNotNull(result);
        assertTrue(result instanceof HistoricalRatesResponse);
        HistoricalRatesResponse response = (HistoricalRatesResponse) result;
        assertEquals("IDR", response.getBase());
        assertNotNull(response.getRates());

        verify(restClient, times(1)).get();
    }

    @Test
    void testGetResourceType() {
        assertEquals("historical_idr_usd", fetcher.getResourceType());
    }
}
