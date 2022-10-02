package com.example.rateapi.service;

import com.example.rateapi.model.Crypto;
import com.example.rateapi.model.Currency;
import com.example.rateapi.service.logger.LoggerService;
import com.example.rateapi.service.providers.CryptoRateProviderChain;
import com.example.rateapi.service.providers.CryptoRateProviderFactory;
import com.example.rateapi.util.cache.CryptoPriceCache;

import static java.lang.String.format;


public class CryptoRateServiceProxy implements CryptoRateService {

    private final CryptoRateProviderChain cryptoRateProviderChain;

    private final CryptoPriceCache cryptoPriceCache;

    private final LoggerService loggerService;

    public CryptoRateServiceProxy(CryptoRateProviderFactory cryptoRateProviderFactory,
                                  CryptoPriceCache cryptoPriceCache,
                                  LoggerService loggerService) {
        cryptoRateProviderChain = cryptoRateProviderFactory.createProvider();
        this.cryptoPriceCache = cryptoPriceCache;
        this.loggerService = loggerService;
        this.loggerService.setOutputClassName(this.getClass().getName());
    }

    public Integer getCryptoRateToLocalCurrency(Crypto crypto, Currency currency) {

        loggerService.logInfo("Getting price from cache...");

        Integer price = cryptoPriceCache.get(crypto, currency);

        if (price != null) {
            loggerService.logDebug(format("Successfully retrieved price from cache. Price - %d for %s in %s", price, crypto, currency));
        } else {
            loggerService.logInfo(format("Have not found price in cache for %s in %s. Getting it from provider", crypto, currency));
            price = cryptoRateProviderChain.getCryptoRateToLocalCurrency(crypto, currency);
            cryptoPriceCache.put(crypto, currency, price);
        }

        loggerService.logDebug(format("Price for %s in %s is %d", crypto, currency, price));

        return price;
    }
}
