package com.example.bitcoingenesis.configuration;

import com.example.bitcoingenesis.client.CoinGeckoCurrencyClient;
import com.example.bitcoingenesis.client.CoinbaseCurrencyClient;
import com.example.bitcoingenesis.client.KucoinCurrencyClient;
import com.example.bitcoingenesis.model.Crypto;
import com.example.bitcoingenesis.model.Currency;
import com.example.bitcoingenesis.model.providers.CryptoRateProviderChain;
import com.example.bitcoingenesis.model.providers.main.coinbase.CoinBaseProviderFactory;
import com.example.bitcoingenesis.model.providers.main.coingecko.CoinGeckoProvider;
import com.example.bitcoingenesis.model.providers.main.coingecko.CoinGeckoProviderFactory;
import com.example.bitcoingenesis.model.providers.CryptoRateProviderFactory;
import com.example.bitcoingenesis.model.providers.exceptional.kucoin.KucoinProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
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

        List<CryptoRateProviderChain> exceptionalProviders = getExceptionalProviders();

        LOGGER.info("Configuring a main crypto rate provider...");

        if (Objects.equals(cryptoRateProvider, "coingecko")) {
            LOGGER.info("Coingecko provider was selected as main crypto rate provider");

            coinGeckoProviderFactory.setExceptionalProviders(exceptionalProviders);

            return coinGeckoProviderFactory;

        } else if (Objects.equals(cryptoRateProvider, "coinbase")) {
            LOGGER.info("Coinbase provider was selected as main crypto rate provider");
            coinBaseProviderFactory.setExceptionalProviders(exceptionalProviders);
            return coinBaseProviderFactory;

        } else {
            LOGGER.error("Crypto rate provider wasn't selected!");
            return null;
        }
    }

    private List<CryptoRateProviderChain> getExceptionalProviders() {
        CryptoRateProviderChain kucoinProvider = kucoinProviderFactory.createProvider();

        List<CryptoRateProviderChain> exceptionalProviders = List.of(kucoinProvider);

        return exceptionalProviders;
    }
}
