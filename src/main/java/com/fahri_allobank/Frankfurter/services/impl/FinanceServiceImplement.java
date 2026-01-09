package com.fahri_allobank.Frankfurter.services.impl;

import org.springframework.stereotype.Service;

import com.fahri_allobank.Frankfurter.services.FinanceService;

@Service
public class FinanceServiceImplement implements FinanceService {

    public FinanceServiceImplement() {

    }

    /**
     * Retrieves data for a specific resource type from the in-memory store.
     * 
     * @param resourceType The type of resource to retrieve
     * @return The stored data
     * @throws IllegalArgumentException if the resource type is not supported
     */
    public Object getDataByResourceType(String resourceType) {
        return null;
    }

}
