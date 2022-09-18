package com.example.bitcoingenesis.model.providers.main.coinbase;

import com.example.bitcoingenesis.client.CoinbaseCurrencyClient;
import com.example.bitcoingenesis.model.providers.CryptoRateProviderChain;
import com.example.bitcoingenesis.model.providers.CryptoRateProviderFactory;
import com.example.bitcoingenesis.model.providers.exceptional.kucoin.KucoinProvider;
import com.example.bitcoingenesis.model.providers.exceptional.kucoin.KucoinProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CoinBaseProviderFactory implements CryptoRateProviderFactory {

    private final CoinbaseCurrencyClient coinbaseCurrencyClient;

    private List<CryptoRateProviderChain> exceptionalProviders;

    private static final Logger LOGGER = LoggerFactory.getLogger(CoinBaseProviderFactory.class);

    public CoinBaseProviderFactory(CoinbaseCurrencyClient coinbaseCurrencyClient) {
        this.coinbaseCurrencyClient = coinbaseCurrencyClient;
        exceptionalProviders = new ArrayList<>();
    }


    @Override
    public CryptoRateProviderChain createProvider() {
        CryptoRateProviderChain mainProvider = new CoinBaseProvider(coinbaseCurrencyClient);

        for (CryptoRateProviderChain exceptionalProvider:exceptionalProviders) {
            mainProvider.setNext(exceptionalProvider);
        }

        LOGGER.info("Coinbase provider was created with exceptional providers - {}", exceptionalProviders);


        return mainProvider;
    }

    @Override
    public void setExceptionalProviders(List<CryptoRateProviderChain> exceptionalProviders) {
        this.exceptionalProviders = exceptionalProviders;
    }
}
