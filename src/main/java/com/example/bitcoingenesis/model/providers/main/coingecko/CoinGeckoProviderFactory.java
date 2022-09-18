package com.example.bitcoingenesis.model.providers.main.coingecko;

import com.example.bitcoingenesis.client.CoinGeckoCurrencyClient;
import com.example.bitcoingenesis.model.providers.CryptoRateProviderChain;
import com.example.bitcoingenesis.model.providers.CryptoRateProviderFactory;
import com.example.bitcoingenesis.model.providers.exceptional.kucoin.KucoinProviderFactory;
import com.example.bitcoingenesis.model.providers.main.coinbase.CoinBaseProvider;
import com.example.bitcoingenesis.model.providers.main.coinbase.CoinBaseProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class CoinGeckoProviderFactory implements CryptoRateProviderFactory {


    private final CoinGeckoCurrencyClient coinGeckoCurrencyClient;

    private List<CryptoRateProviderChain> exceptionalProviders;

    private static final Logger LOGGER = LoggerFactory.getLogger(CoinBaseProviderFactory.class);

    public CoinGeckoProviderFactory(CoinGeckoCurrencyClient coinGeckoCurrencyClient) {
        this.coinGeckoCurrencyClient = coinGeckoCurrencyClient;
        exceptionalProviders = new ArrayList<>();
    }


    @Override
    public CryptoRateProviderChain createProvider() {
        CryptoRateProviderChain mainProvider = new CoinGeckoProvider(coinGeckoCurrencyClient);

        for (CryptoRateProviderChain exceptionalProvider:exceptionalProviders) {
            mainProvider.setNext(exceptionalProvider);
        }

        LOGGER.info("Coingecko provider was created with exceptional providers - {}", exceptionalProviders);

        return mainProvider;
    }

    @Override
    public void setExceptionalProviders(List<CryptoRateProviderChain> exceptionalProviders) {
        this.exceptionalProviders = exceptionalProviders;
    }
}
