package com.fahri_allobank.Frankfurter.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The spread calculator is used to calculate the USD buy spread for IDR.
 */
@Component
public class SpreadCalculator {

    private final String githubUsername;
    private final double spreadFactor;

    public SpreadCalculator(@Value("${github.username}") String githubUsername) {
        this.githubUsername = githubUsername.toLowerCase();
        this.spreadFactor = calculateSpreadFactor();
    }

    /**
     * Calculates the spread factor based on the GitHub username.
     * Formula: (Sum of Unicode Values % 1000) / 100000.0
     * 
     * @return The calculated spread factor
     */
    private double calculateSpreadFactor() {
        int sum = 0;
        for (char c : githubUsername.toCharArray()) {
            sum += (int) c;
        }
        return (sum % 1000) / 100000.0;
    }

    /**
     * Calculates the USD buy spread for IDR.
     * Formula: USD_BuySpread_IDR = (1 / Rate_USD) * (1 + Spread Factor)
     * 
     * @param usdRate The USD rate from the API (when base=IDR)
     * @return The calculated USD buy spread
     */
    public double calculateUsdBuySpread(double usdRate) {
        return (1.0 / usdRate) * (1.0 + spreadFactor);
    }

    public double getSpreadFactor() {
        return spreadFactor;
    }

    public String getGithubUsername() {
        return githubUsername;
    }
}
