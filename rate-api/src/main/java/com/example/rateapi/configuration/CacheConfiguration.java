package com.example.rateapi.configuration;

import com.example.rateapi.service.CryptoRateService;
import com.example.rateapi.service.CryptoRateServiceProxy;
import com.example.rateapi.service.providers.CryptoRateProviderFactory;
import com.example.rateapi.util.cache.CryptoPriceCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfiguration {

    private final CryptoRateProviderFactory cryptoRateProviderFactory;

    private final boolean cachingEnabled;

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheConfiguration.class);

    public CacheConfiguration(CryptoRateProviderFactory cryptoRateProviderFactory,
                              @Value("${feature.cache.price.enabled}") boolean cachingEnabled) {
        this.cryptoRateProviderFactory = cryptoRateProviderFactory;
        this.cachingEnabled = cachingEnabled;
    }

    @Bean
    public CryptoRateService cryptoRateService() {

        LOGGER.info("Caching feature enabled - {}", cachingEnabled);

        if (cachingEnabled) {
            LOGGER.info("Configuring price cache...");
            return new CryptoRateServiceProxy(cryptoRateProviderFactory, new CryptoPriceCache());
        } else {
            return cryptoRateProviderFactory.createProvider();
        }
    }

}
