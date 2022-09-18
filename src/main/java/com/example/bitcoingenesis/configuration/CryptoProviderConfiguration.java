package com.example.bitcoingenesis.configuration;

import com.example.bitcoingenesis.service.rate.providers.CryptoRateProviderChain;
import com.example.bitcoingenesis.service.rate.providers.coinbase.CoinBaseProviderFactory;
import com.example.bitcoingenesis.service.rate.providers.coingecko.CoinGeckoProviderFactory;
import com.example.bitcoingenesis.service.rate.providers.CryptoRateProviderFactory;
import com.example.bitcoingenesis.service.rate.providers.kucoin.KucoinProviderFactory;
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

    private final KucoinProviderFactory kucoinProviderFactory;
    private final CoinGeckoProviderFactory coinGeckoProviderFactory;
    private final CoinBaseProviderFactory coinBaseProviderFactory;

    public CryptoProviderConfiguration(KucoinProviderFactory kucoinProviderFactory,
                                       CoinGeckoProviderFactory coinGeckoProviderFactory,
                                       CoinBaseProviderFactory coinBaseProviderFactory) {
        this.kucoinProviderFactory = kucoinProviderFactory;
        this.coinGeckoProviderFactory = coinGeckoProviderFactory;
        this.coinBaseProviderFactory = coinBaseProviderFactory;
    }

    private final static Logger LOGGER = LoggerFactory.getLogger(CryptoProviderConfiguration.class);

    @Bean
    public CryptoRateProviderFactory cryptoRateProviderFactory() {

        LOGGER.info("Configuring a main crypto rate provider...");
        CryptoRateProviderChain cryptoRateExceptionalProviderChain = getExceptionalCryptoRateProviderChain();

        if (Objects.equals(cryptoRateProvider, "coingecko")) {
            LOGGER.info("Coingecko provider was selected as main crypto rate provider");
            coinGeckoProviderFactory.setCryptoProviderExceptionalChain(cryptoRateExceptionalProviderChain);
            LOGGER.info("Exceptional crypto rate provider chain was set to coingecko provider");
            return coinGeckoProviderFactory;

        } else if (Objects.equals(cryptoRateProvider, "coinbase")) {
            LOGGER.info("Coinbase provider was selected as main crypto rate provider");
            coinBaseProviderFactory.setCryptoProviderExceptionalChain(cryptoRateExceptionalProviderChain);
            LOGGER.info("Exceptional crypto rate provider chain was set to coinbase provider");
            return coinBaseProviderFactory;

        } else {
            throw new IllegalStateException("Crypto rate provider wasn't selected!");
        }
    }

    @Bean
    public CryptoRateProviderChain getExceptionalCryptoRateProviderChain() {

        CryptoRateProviderChain exceptionalProviderChain = kucoinProviderFactory.createProvider();
        LOGGER.info("{} - Exceptional provider for main was created", exceptionalProviderChain);

        if (Objects.equals(cryptoRateProvider, "coinbase")) {
            CryptoRateProviderChain coingecko = coinGeckoProviderFactory.createProvider();
            exceptionalProviderChain.setNext(coingecko);
            LOGGER.info("Exceptional provider for - {} is {}", exceptionalProviderChain, coingecko);
        } else if (Objects.equals(cryptoRateProvider, "coingecko")) {
            CryptoRateProviderChain coinbaseProvider = coinBaseProviderFactory.createProvider();
            exceptionalProviderChain.setNext(coinbaseProvider);
            LOGGER.info("Exceptional provider for - {} is {}", exceptionalProviderChain, coinbaseProvider);
        }

        LOGGER.info("Exceptional crypto rate provider chain was configured");

        return exceptionalProviderChain;
    }
}
