package com.example.bitcoingenesis.model.providers.coingecko;

import com.example.bitcoingenesis.client.CoinGeckoCurrencyClient;
import com.example.bitcoingenesis.model.providers.CryptoRateProvider;
import com.example.bitcoingenesis.model.providers.CryptoRateProviderFactory;
import com.example.bitcoingenesis.model.providers.coingecko.CoinGeckoProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CoinGeckoProviderFactory implements CryptoRateProviderFactory {

    @Autowired
    private CoinGeckoCurrencyClient coinGeckoCurrencyClient;

    @Override
    public CryptoRateProvider createProvider() {
        return new CoinGeckoProvider(coinGeckoCurrencyClient);
    }

}
