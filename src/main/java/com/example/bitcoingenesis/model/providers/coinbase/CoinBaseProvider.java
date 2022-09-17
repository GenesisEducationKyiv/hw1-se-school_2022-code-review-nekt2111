package com.example.bitcoingenesis.model.providers.coinbase;

import com.example.bitcoingenesis.client.CoinbaseCurrencyClient;
import com.example.bitcoingenesis.model.Crypto;
import com.example.bitcoingenesis.model.Currency;
import com.example.bitcoingenesis.model.providers.CryptoRateProvider;

public class CoinBaseProvider extends CryptoRateProvider {

    private final CoinbaseCurrencyClient coinbaseCurrencyClient;

    public CoinBaseProvider(CoinbaseCurrencyClient coinbaseCurrencyClient) {
        this.coinbaseCurrencyClient = coinbaseCurrencyClient;
    }


    @Override
    public Integer getCryptoRateToLocalCurrency(Crypto crypto, Currency currency) {
        return coinbaseCurrencyClient.getCryptoRateToLocalCurrency(crypto, currency);
    }
}
