package com.example.bitcoingenesis.configuration;

import com.example.bitcoingenesis.model.providers.coinbase.CoinBaseProviderFactory;
import com.example.bitcoingenesis.model.providers.coingecko.CoinGeckoProviderFactory;
import com.example.bitcoingenesis.model.providers.CryptoRateProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class CryptoProviderConfiguration {

    @Value(value = "${crypto.rate.provider}")
    private String cryptoRateProvider;

    private final static Logger LOGGER = LoggerFactory.getLogger(CryptoProviderConfiguration.class);

    @Bean
    public CryptoRateProviderFactory cryptoRateProviderFactory() {

        LOGGER.info("Configuring a crypto rate provider...");

        if (Objects.equals(cryptoRateProvider, "coingecko")) {
            LOGGER.info("Coingecko provider was selected as crypto rate provider");
            return new CoinGeckoProviderFactory();
        }
        else if (Objects.equals(cryptoRateProvider, "coinbase")) {
            LOGGER.info("Coinbase provider was selected as crypto rate provider");
            return new CoinBaseProviderFactory();
        }
        else {
            LOGGER.error("Crypto rate provider wasn't selected!");
            return null;
        }
    }

}
