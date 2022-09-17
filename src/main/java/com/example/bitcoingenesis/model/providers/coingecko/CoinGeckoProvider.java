package com.example.bitcoingenesis.model.providers.coingecko;

import com.example.bitcoingenesis.client.CoinGeckoCurrencyClient;
import com.example.bitcoingenesis.model.Crypto;
import com.example.bitcoingenesis.model.Currency;
import com.example.bitcoingenesis.model.providers.CryptoRateProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoinGeckoProvider extends CryptoRateProvider {

    private final static Logger LOGGER = LoggerFactory.getLogger(CoinGeckoProvider.class);

    private final CoinGeckoCurrencyClient coinGeckoCurrencyClient;
    public CoinGeckoProvider(CoinGeckoCurrencyClient coinGeckoCurrencyClient) {
        this.coinGeckoCurrencyClient = coinGeckoCurrencyClient;
        LOGGER.info("Coingecko provider was created and configured");
    }

    @Override
    public Integer getCryptoRateToLocalCurrency(Crypto crypto, Currency currency) {
        return coinGeckoCurrencyClient.getCryptoRateToLocalCurrency(crypto, currency);
    }
}
