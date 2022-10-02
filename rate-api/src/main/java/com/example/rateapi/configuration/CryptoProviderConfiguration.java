package com.example.rateapi.configuration;

import com.example.rateapi.service.logger.LoggerService;
import com.example.rateapi.service.providers.CryptoRateProviderChain;
import com.example.rateapi.service.providers.CryptoRateProviderFactory;
import com.example.rateapi.service.providers.coinbase.CoinBaseProviderFactory;
import com.example.rateapi.service.providers.coingecko.CoinGeckoProviderFactory;
import com.example.rateapi.service.providers.kucoin.KucoinProviderFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Objects;

import static java.lang.String.format;

@Configuration
public class CryptoProviderConfiguration {

    @Value(value = "${crypto.rate.provider}")
    private String cryptoRateProvider;

    private final KucoinProviderFactory kucoinProviderFactory;
    private final CoinGeckoProviderFactory coinGeckoProviderFactory;
    private final CoinBaseProviderFactory coinBaseProviderFactory;

    private final LoggerService loggerService;

    public CryptoProviderConfiguration(KucoinProviderFactory kucoinProviderFactory,
                                       CoinGeckoProviderFactory coinGeckoProviderFactory,
                                       CoinBaseProviderFactory coinBaseProviderFactory,
                                       LoggerService loggerService) {
        this.kucoinProviderFactory = kucoinProviderFactory;
        this.coinGeckoProviderFactory = coinGeckoProviderFactory;
        this.coinBaseProviderFactory = coinBaseProviderFactory;
        this.loggerService = loggerService;
        this.loggerService.setOutputClassName(this.getClass().getName());
    }

    @Bean
    public CryptoRateProviderFactory cryptoRateProviderFactory() {

        loggerService.logInfo("Configuring a main crypto rate provider...");
        CryptoRateProviderChain cryptoRateExceptionalProviderChain = getExceptionalCryptoRateProviderChain();

        if (Objects.equals(cryptoRateProvider, "coingecko")) {
            loggerService.logInfo("Coingecko provider was selected as main crypto rate provider");
            coinGeckoProviderFactory.setCryptoProviderExceptionalChain(cryptoRateExceptionalProviderChain);
            loggerService.logInfo("Exceptional crypto rate provider chain was set to coingecko provider");
            return coinGeckoProviderFactory;

        } else if (Objects.equals(cryptoRateProvider, "coinbase")) {
            loggerService.logInfo("Coinbase provider was selected as main crypto rate provider");
            coinBaseProviderFactory.setCryptoProviderExceptionalChain(cryptoRateExceptionalProviderChain);
            loggerService.logInfo("Exceptional crypto rate provider chain was set to coinbase provider");
            return coinBaseProviderFactory;

        } else {
            throw new IllegalStateException("Crypto rate provider wasn't selected!");
        }
    }

    @Bean
    public CryptoRateProviderChain getExceptionalCryptoRateProviderChain() {

        CryptoRateProviderChain exceptionalProviderChain = kucoinProviderFactory.createProvider();
        loggerService.logInfo(format("%s - Exceptional provider for main was created", exceptionalProviderChain));

        if (Objects.equals(cryptoRateProvider, "coinbase")) {
            CryptoRateProviderChain coingecko = coinGeckoProviderFactory.createProvider();
            exceptionalProviderChain.setNext(coingecko);
            loggerService.logInfo(format("Exceptional provider for - %s is %s", exceptionalProviderChain, coingecko));
        } else if (Objects.equals(cryptoRateProvider, "coingecko")) {
            CryptoRateProviderChain coinbaseProvider = coinBaseProviderFactory.createProvider();
            exceptionalProviderChain.setNext(coinbaseProvider);
            loggerService.logInfo(format("Exceptional provider for - %s is %s", exceptionalProviderChain, coinbaseProvider));
        }

        loggerService.logInfo("Exceptional crypto rate provider chain was configured");

        return exceptionalProviderChain;
    }
}
