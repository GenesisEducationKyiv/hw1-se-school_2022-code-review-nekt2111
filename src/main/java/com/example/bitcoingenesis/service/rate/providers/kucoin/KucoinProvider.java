package com.example.bitcoingenesis.service.rate.providers.kucoin;

import com.example.bitcoingenesis.client.CryptoCurrencyClient;
import com.example.bitcoingenesis.client.CryptoCurrencyClientDecorator;
import com.example.bitcoingenesis.client.KucoinCurrencyClient;
import com.example.bitcoingenesis.model.Crypto;
import com.example.bitcoingenesis.model.Currency;
import com.example.bitcoingenesis.service.rate.providers.CryptoRateProviderChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KucoinProvider extends CryptoRateProviderChain {

    private final CryptoCurrencyClient cryptoCurrencyClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(KucoinProvider.class);

    public KucoinProvider(KucoinCurrencyClient kucoinCurrencyClient) {
        this.cryptoCurrencyClient = new CryptoCurrencyClientDecorator(kucoinCurrencyClient);
    }

    @Override
    public Integer getCryptoRateToLocalCurrency(Crypto cryptocurrencyName, Currency currency) {

        try {
            return cryptoCurrencyClient.getCryptoRateToLocalCurrency(cryptocurrencyName, currency).getPrice();
        } catch (Exception e) {
            LOGGER.info("Error occurred while getting data from {} with error message - {}", this, e.getMessage());
            LOGGER.info("Getting data from an exceptional provider - {}", next);
            return next.getCryptoRateToLocalCurrency(cryptocurrencyName, currency);
        }
    }

    @Override
    public String toString() {
        return "Kucoin Provider";
    }
}
