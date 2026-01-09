package com.fahri_allobank.Frankfurter.services;

public interface FinanceService {
    /**
     * Retrieves data for a specific resource type from the in-memory store.
     * 
     * @param resourceType The type of resource to retrieve
     * @return The stored data
     * @throws IllegalArgumentException if the resource type is not supported
     */
    public Object getDataByResourceType(String resourceType);
}
