package com.fahri_allobank.Frankfurter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * DTO for the latest exchange rates response from Frankfurter API
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LatestRatesResponse {
    private Double amount;
    private String base;
    private String date;
    private Map<String, Double> rates;

    @JsonProperty("USD_BuySpread_IDR")
    private Double usdBuySpreadIdr;
}
