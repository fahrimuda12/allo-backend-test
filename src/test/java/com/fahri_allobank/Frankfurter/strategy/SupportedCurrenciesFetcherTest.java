package com.fahri_allobank.Frankfurter.strategy;

import com.fahri_allobank.Frankfurter.dto.SupportedCurrenciesResponse;
import com.fahri_allobank.Frankfurter.strategy.impl.SupportedCurrenciesFetcher;

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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupportedCurrenciesFetcherTest {

    @Mock
    private RestClient restClient;

    @Mock
    private RestClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private RestClient.ResponseSpec responseSpec;

    private SupportedCurrenciesFetcher fetcher;

    @BeforeEach
    void setUp() {
        fetcher = new SupportedCurrenciesFetcher(restClient);
    }

    @Test
    void testFetchDataSuccess() {
        // Prepare mock response
        Map<String, String> mockCurrencies = new HashMap<>();
        mockCurrencies.put("USD", "United States Dollar");
        mockCurrencies.put("EUR", "Euro");
        mockCurrencies.put("IDR", "Indonesian Rupiah");

        // Setup mock behavior
        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(eq(Map.class))).thenReturn(mockCurrencies);

        // Execute
        Object result = fetcher.fetchData();

        // Verify
        assertNotNull(result);
        assertTrue(result instanceof SupportedCurrenciesResponse);
        SupportedCurrenciesResponse response = (SupportedCurrenciesResponse) result;
        assertNotNull(response.getCurrencies());
        assertEquals(3, response.getCurrencies().size());
        assertTrue(response.getCurrencies().containsKey("USD"));

        verify(restClient, times(1)).get();
    }

    @Test
    void testGetResourceType() {
        assertEquals("supported_currencies", fetcher.getResourceType());
    }
}
