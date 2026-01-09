package com.fahri_allobank.Frankfurter.services.impl;

import org.springframework.stereotype.Service;

import com.fahri_allobank.Frankfurter.services.FinanceDataStore;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FinanceDataStoreImplement implements FinanceDataStore {

    private final Map<String, Object> dataStore = new ConcurrentHashMap<>();
    private volatile boolean initialized = false;

    public void storeData(String resourceType, Object data) {
        if (initialized) {
            throw new IllegalStateException("Data store has already been initialized and is immutable");
        }
        dataStore.put(resourceType, data);
    }

    public void markAsInitialized() {
        this.initialized = true;
    }

    public Object getData(String resourceType) {
        return dataStore.get(resourceType);
    }

    public Map<String, Object> getAllData() {
        return Collections.unmodifiableMap(dataStore);
    }

    public boolean isInitialized() {
        return initialized;
    }
}
