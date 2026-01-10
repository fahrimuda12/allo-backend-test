package com.fahri_allobank.Frankfurter.services;

import java.util.Map;

public interface FinanceDataStore {

    /**
     * Stores data for a specific resource type.
     * This method can only be called during initialization (before the store is
     * marked as initialized).
     * 
     * @param resourceType The type of resource
     * @param data         The data to store
     * @throws IllegalStateException if the store has already been initialized
     */
    public void storeData(String resourceType, Object data);

    /**
     * Marks the data store as initialized, making it immutable.
     * After this method is called, no more data can be added to the store.
     */
    public void markAsInitialized();

    /**
     * Retrieves data for a specific resource type.
     * 
     * @param resourceType The type of resource to retrieve
     * @return The stored data, or null if not found
     */
    public Object getData(String resourceType);

    /**
     * Returns an unmodifiable view of all stored data.
     * 
     * @return Unmodifiable map of all data
     */
    public Map<String, Object> getAllData();

    /**
     * Checks if the data store has been initialized.
     * 
     * @return true if initialized, false otherwise
     */
    public boolean isInitialized();
}
