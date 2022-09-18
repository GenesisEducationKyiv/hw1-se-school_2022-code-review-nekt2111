package com.example.bitcoingenesis.service.rate.providers.exceptional.kucoin;

import com.example.bitcoingenesis.client.KucoinCurrencyClient;
import com.example.bitcoingenesis.model.Crypto;
import com.example.bitcoingenesis.model.Currency;
import com.example.bitcoingenesis.service.rate.providers.CryptoRateProviderChain;

public class KucoinProvider extends CryptoRateProviderChain {

    private final KucoinCurrencyClient kucoinCurrencyClient;

    public KucoinProvider(KucoinCurrencyClient kucoinCurrencyClient) {
        this.kucoinCurrencyClient = kucoinCurrencyClient;
    }

    @Override
    public Integer getCryptoRateToLocalCurrency(Crypto cryptocurrencyName, Currency currency) {
        return kucoinCurrencyClient.getCryptoRateToLocalCurrency(cryptocurrencyName, currency);
    }

    @Override
    public String toString() {
        return "Kucoin Provider";
    }
}
