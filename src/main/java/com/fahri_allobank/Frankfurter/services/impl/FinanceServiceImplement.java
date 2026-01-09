package com.fahri_allobank.Frankfurter.services.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fahri_allobank.Frankfurter.services.FinanceService;
import com.fahri_allobank.Frankfurter.services.FinanceDataStore;
import com.fahri_allobank.Frankfurter.strategy.IDRDataFetcher;

@Service
public class FinanceServiceImplement implements FinanceService {

    private final FinanceDataStore dataStore;
    private final Map<String, IDRDataFetcher> strategyMap;

    public FinanceServiceImplement(FinanceDataStore dataStore, List<IDRDataFetcher> dataFetchers) {
        this.dataStore = dataStore;
        this.strategyMap = dataFetchers.stream()
                .collect(Collectors.toMap(
                        IDRDataFetcher::getResourceType,
                        Function.identity()));
    }

    public Object getDataByResourceType(String resourceType) {
        if (!strategyMap.containsKey(resourceType)) {
            throw new IllegalArgumentException("Unsupported resource type: " + resourceType);
        }

        Object data = dataStore.getData(resourceType);

        if (data == null) {
            throw new IllegalStateException("Data not found for resource type: " + resourceType);
        }

        return data;
    }

}
