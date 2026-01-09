package com.fahri_allobank.util;

import org.junit.jupiter.api.Test;

import com.fahri_allobank.Frankfurter.util.SpreadCalculator;

import static org.junit.jupiter.api.Assertions.*;

class SpreadCalculatorTest {

    @Test
    void testSpreadFactorCalculation() {
        // Test with the actual GitHub username
        SpreadCalculator calculator = new SpreadCalculator("fahri-allobank");

        // Calculate expected spread factor
        String username = "fahri-allobank";
        int sum = 0;
        for (char c : username.toCharArray()) {
            sum += (int) c;
        }
        double expectedSpreadFactor = (sum % 1000) / 100000.0;

        assertEquals(expectedSpreadFactor, calculator.getSpreadFactor(), 0.00001);
        assertTrue(calculator.getSpreadFactor() >= 0.0 && calculator.getSpreadFactor() <= 0.00999);
    }

    @Test
    void testUsdBuySpreadCalculation() {
        SpreadCalculator calculator = new SpreadCalculator("test");

        // Test with a sample USD rate
        double usdRate = 0.000063; // Example rate when base=IDR
        double spreadFactor = calculator.getSpreadFactor();
        double expectedBuySpread = (1.0 / usdRate) * (1.0 + spreadFactor);

        assertEquals(expectedBuySpread, calculator.calculateUsdBuySpread(usdRate), 0.01);
    }

    @Test
    void testDifferentUsernamesProduceDifferentSpreadFactors() {
        SpreadCalculator calc1 = new SpreadCalculator("user1");
        SpreadCalculator calc2 = new SpreadCalculator("user2");

        assertNotEquals(calc1.getSpreadFactor(), calc2.getSpreadFactor());
    }
}
