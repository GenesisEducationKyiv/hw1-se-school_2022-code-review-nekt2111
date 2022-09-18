package com.example.bitcoingenesis.service.rate.providers.main.coinbase;

import com.example.bitcoingenesis.client.CoinbaseCurrencyClient;
import com.example.bitcoingenesis.model.Crypto;
import com.example.bitcoingenesis.model.Currency;
import com.example.bitcoingenesis.service.rate.CryptoRateService;
import com.example.bitcoingenesis.service.rate.providers.CryptoRateProviderChain;
import com.example.bitcoingenesis.service.rate.providers.main.coingecko.CoinGeckoProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoinBaseProvider extends CryptoRateProviderChain  {

    private final CoinbaseCurrencyClient coinbaseCurrencyClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(CoinGeckoProvider.class);

    public CoinBaseProvider(CoinbaseCurrencyClient coinbaseCurrencyClient) {
        this.coinbaseCurrencyClient = coinbaseCurrencyClient;
    }


    @Override
    public Integer getCryptoRateToLocalCurrency(Crypto crypto, Currency currency) {
        try {
            return coinbaseCurrencyClient.getCryptoRateToLocalCurrency(crypto, currency);
        } catch (Exception e) {
            LOGGER.info("Error occurred while getting data from {} with error message - {}", this, e.getMessage());
            LOGGER.info("Getting data from an exceptional provider - {}", next);
            return next.getCryptoRateToLocalCurrency(crypto, currency);
        }
    }
    @Override
    public String toString() {
        return "CoinBase Provider";
    }
}
