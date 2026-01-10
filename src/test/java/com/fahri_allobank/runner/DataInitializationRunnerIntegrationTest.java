package com.fahri_allobank.Frankfurter.runner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fahri_allobank.Frankfurter.services.FinanceDataStore;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test to verify that the ApplicationRunner successfully
 * initializes and loads data into the in-memory store before the
 * application context is ready.
 */
@SpringBootTest
class DataInitializationRunnerIntegrationTest {

    @Autowired
    private FinanceDataStore dataStore;

    @Test
    void testDataStoreIsInitializedOnStartup() {
        // Verify that the data store has been initialized
        assertTrue(dataStore.isInitialized(), "Data store should be initialized on startup");
    }

    @Test
    void testAllResourceTypesAreLoaded() {
        // Verify that all three resource types have been loaded
        assertNotNull(dataStore.getData("latest_idr_rates"),
                "Latest IDR rates should be loaded");
        assertNotNull(dataStore.getData("historical_idr_usd"),
                "Historical IDR to USD rates should be loaded");
        assertNotNull(dataStore.getData("supported_currencies"),
                "Supported currencies should be loaded");
    }

    @Test
    void testDataStoreIsImmutable() {
        // Verify that attempting to add new data after initialization throws an
        // exception
        assertThrows(IllegalStateException.class, () -> {
            dataStore.storeData("new_resource", new Object());
        }, "Data store should be immutable after initialization");
    }
}
