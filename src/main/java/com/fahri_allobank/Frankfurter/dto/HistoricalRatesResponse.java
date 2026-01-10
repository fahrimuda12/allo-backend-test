package com.fahri_allobank.Frankfurter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * DTO for historical exchange rates response from Frankfurter API
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricalRatesResponse {
    private Double amount;
    private String base;
    private String start_date;
    private String end_date;
    private Map<String, Map<String, Double>> rates;
}
