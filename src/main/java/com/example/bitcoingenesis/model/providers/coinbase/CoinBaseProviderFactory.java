package com.example.bitcoingenesis.model.providers.coinbase;

import com.example.bitcoingenesis.client.CoinbaseCurrencyClient;
import com.example.bitcoingenesis.model.providers.CryptoRateProvider;
import com.example.bitcoingenesis.model.providers.CryptoRateProviderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CoinBaseProviderFactory implements CryptoRateProviderFactory {

    @Autowired
    private CoinbaseCurrencyClient coinbaseCurrencyClient;


    @Override
    public CryptoRateProvider createProvider() {
        return new CoinBaseProvider(coinbaseCurrencyClient);
    }
}
