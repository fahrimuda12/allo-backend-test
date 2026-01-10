package com.fahri_allobank.Frankfurter.config;

public class Constants {
    public static final String USD = "USD";
    public static final String IDR = "IDR";

    public static class ResponseCode {
        public static final String BAD_REQUEST = "BAD_REQUEST";
        public static final String DATA_FETCH_ERROR = "DATA_FETCH_ERROR";
    }

    public static class ResourceType {
        public static final String HISTORICAL_IDR_USD = "historical_idr_usd";
        public static final String LATEST_IDR_RATES = "latest_idr_rates";
        public static final String SUPPORTED_CURRENCIES = "supported_currencies";

    }
}
