package com.fahri_allobank.Frankfurter.strategy;

public interface IDRDataFetcher {

    /**
     * Fetches data from the external API and returns it as a generic Object.
     * The actual return type will vary based on the concrete strategy
     * implementation.
     * 
     * @return The fetched and potentially transformed data
     */
    Object fetchData();

    /**
     * Returns the resource type identifier that this strategy handles.
     * 
     * @return The resource type string (e.g., "latest_idr_rates")
     */
    String getResourceType();
}
