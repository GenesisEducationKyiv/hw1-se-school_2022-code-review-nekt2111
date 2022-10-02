package com.example.rateapi.client;

import com.example.rateapi.model.Crypto;
import com.example.rateapi.model.CryptoPriceInfo;
import com.example.rateapi.model.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CryptoCurrencyClientDecorator implements CryptoCurrencyClient {

    private final CryptoCurrencyClient cryptoCurrencyClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoCurrencyClientDecorator.class);

    public CryptoCurrencyClientDecorator(CryptoCurrencyClient cryptoCurrencyClient) {
        this.cryptoCurrencyClient = cryptoCurrencyClient;
    }

    @Override
    public CryptoPriceInfo getCryptoRateToLocalCurrency(Crypto crypto, Currency currency) {

        LOGGER.info("Making request to {} for crypto {} in currency {} ({})", getApiUrl(), crypto.getFullName().toUpperCase(), currency, currency.getFullName());
        CryptoPriceInfo cryptoPriceInfo = cryptoCurrencyClient.getCryptoRateToLocalCurrency(crypto, currency);
        LOGGER.info("Response from {} is {}", cryptoCurrencyClient, cryptoPriceInfo);
        return cryptoPriceInfo;
    }

    @Override
    public String getApiUrl() {
        return cryptoCurrencyClient.getApiUrl();
    }
}
