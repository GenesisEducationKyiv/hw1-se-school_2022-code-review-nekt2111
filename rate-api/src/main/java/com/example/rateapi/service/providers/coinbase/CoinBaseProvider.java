package com.example.rateapi.service.providers.coinbase;


import com.example.rateapi.client.CoinbaseCurrencyClient;
import com.example.rateapi.client.CryptoCurrencyClient;
import com.example.rateapi.client.CryptoCurrencyClientDecorator;
import com.example.rateapi.model.Crypto;
import com.example.rateapi.model.Currency;
import com.example.rateapi.service.providers.CryptoRateProviderChain;
import com.example.rateapi.service.providers.coingecko.CoinGeckoProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoinBaseProvider extends CryptoRateProviderChain {

    private final CryptoCurrencyClient cryptoCurrencyClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(CoinGeckoProvider.class);

    public CoinBaseProvider(CoinbaseCurrencyClient coinbaseCurrencyClient) {
        this.cryptoCurrencyClient = new CryptoCurrencyClientDecorator(coinbaseCurrencyClient);
    }

    @Override
    public Integer getCryptoRateToLocalCurrency(Crypto crypto, Currency currency) {
        try {
            return cryptoCurrencyClient.getCryptoRateToLocalCurrency(crypto, currency).getPrice();
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
