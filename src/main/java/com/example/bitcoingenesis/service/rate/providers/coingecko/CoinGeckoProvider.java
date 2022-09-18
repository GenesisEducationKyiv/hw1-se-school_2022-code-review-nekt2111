package com.example.bitcoingenesis.service.rate.providers.coingecko;

import com.example.bitcoingenesis.client.CoinGeckoCurrencyClient;
import com.example.bitcoingenesis.client.CryptoCurrencyClient;
import com.example.bitcoingenesis.client.CryptoCurrencyClientDecorator;
import com.example.bitcoingenesis.model.Crypto;
import com.example.bitcoingenesis.model.Currency;
import com.example.bitcoingenesis.service.rate.providers.CryptoRateProviderChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoinGeckoProvider extends CryptoRateProviderChain {

    private final static Logger LOGGER = LoggerFactory.getLogger(CoinGeckoProvider.class);

    private final CryptoCurrencyClient cryptoCurrencyClient;


    public CoinGeckoProvider(CoinGeckoCurrencyClient coinGeckoCurrencyClient) {
        this.cryptoCurrencyClient = new CryptoCurrencyClientDecorator(coinGeckoCurrencyClient);
        LOGGER.info("Coingecko provider was created and configured");
    }

    @Override
    public Integer getCryptoRateToLocalCurrency(Crypto crypto, Currency currency) {
        try {
            return cryptoCurrencyClient.getCryptoRateToLocalCurrency(crypto, currency).getPrice();
        } catch (Exception e) {
            LOGGER.info("Error occurred while getting data from {} with error message - {}", this, e.getMessage());
            LOGGER.info("Getting data from an exceptional provider - {}", next);
            return next.getCryptoRateToLocalCurrency(crypto ,currency);
        }
    }

    @Override
    public String toString() {
        return "CoinGecko Provider";
    }
}
