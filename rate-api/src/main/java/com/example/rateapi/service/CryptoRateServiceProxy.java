package com.example.rateapi.service;

import com.example.rateapi.model.Crypto;
import com.example.rateapi.model.Currency;
import com.example.rateapi.service.providers.CryptoRateProviderChain;
import com.example.rateapi.service.providers.CryptoRateProviderFactory;
import com.example.rateapi.util.cache.CryptoPriceCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CryptoRateServiceProxy implements CryptoRateService {

    private final CryptoRateProviderChain cryptoRateProviderChain;

    private final CryptoPriceCache cryptoPriceCache;

    private final static Logger LOGGER = LoggerFactory.getLogger(CryptoRateServiceProxy.class);

    public CryptoRateServiceProxy(CryptoRateProviderFactory cryptoRateProviderFactory,
                                  CryptoPriceCache cryptoPriceCache) {
        cryptoRateProviderChain = cryptoRateProviderFactory.createProvider();
        this.cryptoPriceCache = cryptoPriceCache;
    }

    public Integer getCryptoRateToLocalCurrency(Crypto crypto, Currency currency) {

        LOGGER.info("Getting price from cache...");

        Integer price = cryptoPriceCache.get(crypto, currency);

        if (price != null) {
            LOGGER.info("Successfully retrieved price from cache. Price - {} for {} in {}", price, crypto, currency);
        } else {
            LOGGER.info("Have not found price in cache for {} in {}. Getting it from provider", crypto, currency);
            price = cryptoRateProviderChain.getCryptoRateToLocalCurrency(crypto, currency);
            cryptoPriceCache.put(crypto, currency, price);
        }

        LOGGER.info("Price for {} in {} is {}", crypto, currency, price);

        return price;
    }
}
