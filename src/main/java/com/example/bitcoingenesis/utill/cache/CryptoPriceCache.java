package com.example.bitcoingenesis.utill.cache;

import com.example.bitcoingenesis.model.Crypto;
import com.example.bitcoingenesis.model.Currency;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class CryptoPriceCache {

    private static final int EXPIRES_IN_MINUTES = 5;

    private static final int CACHE_SIZE_TO_CLEAN_UP = 1000;

    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoPriceCache.class);

    private Cache<CryptoCurrencyPair, Integer> cache;

    public CryptoPriceCache() {
        this.setUpCache();
    }

    public void put(Crypto crypto, Currency currency, Integer price) {
        CryptoCurrencyPair cryptoCurrencyPair = new CryptoCurrencyPair(crypto, currency);

        if (cache.size() >= CACHE_SIZE_TO_CLEAN_UP) {
            cache.cleanUp();
            LOGGER.info("Old values were cleaned up");
        }

        cache.put(cryptoCurrencyPair, price);
        LOGGER.info("Put price - {} for {} in {} in cache for {} minutes", price, crypto, currency, EXPIRES_IN_MINUTES);
    }

    public Integer get(Crypto crypto, Currency currency) {
        CryptoCurrencyPair cryptoCurrencyPair = new CryptoCurrencyPair(crypto, currency);
        return cache.getIfPresent(cryptoCurrencyPair);
    }

    private void setUpCache() {
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRES_IN_MINUTES, TimeUnit.MINUTES)
                .build();
        LOGGER.info("Successfully configured price cache");
    }

}
