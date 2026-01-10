package com.fahri_allobank.Frankfurter.runner;

import com.fahri_allobank.Frankfurter.services.FinanceDataStore;
import com.fahri_allobank.Frankfurter.strategy.IDRDataFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ApplicationRunner that loads all finance data from external API on
 * application startup.
 * This ensures data is fetched exactly once and stored in memory before the
 * application
 * is ready to serve requests.
 * 
 * ApplicationRunner is preferred over CommandLineRunner because it provides
 * access to
 * parsed application arguments, and is preferred over @PostConstruct because it
 * runs
 * after the entire application context is fully initialized, ensuring all beans
 * are ready.
 */
@Component
public class DataInitializationRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializationRunner.class);

    private final List<IDRDataFetcher> dataFetchers;
    private final FinanceDataStore dataStore;

    public DataInitializationRunner(List<IDRDataFetcher> dataFetchers, FinanceDataStore dataStore) {
        this.dataFetchers = dataFetchers;
        this.dataStore = dataStore;
    }

    @Override
    public void run(ApplicationArguments args) {
        logger.info("Starting data initialization from Frankfurter API...");

        try {
            for (IDRDataFetcher fetcher : dataFetchers) {
                String resourceType = fetcher.getResourceType();
                logger.info("Fetching data for resource type: {}", resourceType);

                Object data = fetcher.fetchData();
                dataStore.storeData(resourceType, data);

                logger.info("Successfully stored data for resource type: {}", resourceType);
            }

            dataStore.markAsInitialized();

            logger.info("Data initialization completed successfully. Store is now immutable.");
        } catch (Exception e) {
            logger.error("Failed to initialize data from Frankfurter API", e);
            throw new RuntimeException("Application startup failed due to data initialization error", e);
        }
    }
}
