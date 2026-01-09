package com.fahri_allobank.Frankfurter.config;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RestClientFactoryBean implements FactoryBean<RestClient> {

    private final FrankfurterApiProperties properties;

    public RestClientFactoryBean(FrankfurterApiProperties properties) {
        this.properties = properties;
    }

    @Override
    public RestClient getObject() {
        return RestClient.builder()
                .baseUrl(properties.getBaseUrl())
                .build();
    }

    @Override
    public Class<?> getObjectType() {
        return RestClient.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
