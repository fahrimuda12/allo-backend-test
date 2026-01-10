package com.fahri_allobank.Frankfurter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * DTO for supported currencies response from Frankfurter API
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupportedCurrenciesResponse {
    private Map<String, String> currencies;
}
