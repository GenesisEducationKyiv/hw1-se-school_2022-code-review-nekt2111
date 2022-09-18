package com.example.bitcoingenesis.model.providers.exceptional.kucoin;

import com.example.bitcoingenesis.client.KucoinCurrencyClient;
import com.example.bitcoingenesis.model.providers.CryptoRateProviderChain;
import com.example.bitcoingenesis.model.providers.CryptoRateProviderFactory;
import com.example.bitcoingenesis.model.providers.main.coingecko.CoinGeckoProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class KucoinProviderFactory implements CryptoRateProviderFactory {

    private final KucoinCurrencyClient kucoinCurrencyClient;

    private List<CryptoRateProviderChain> exceptionalProviders;

    private static final Logger LOGGER = LoggerFactory.getLogger(KucoinProviderFactory.class);

    public KucoinProviderFactory(KucoinCurrencyClient kucoinCurrencyClient) {
        this.kucoinCurrencyClient = kucoinCurrencyClient;
        exceptionalProviders = new ArrayList<>();
    }

    @Override
    public CryptoRateProviderChain createProvider() {
        CryptoRateProviderChain mainProvider = new KucoinProvider(kucoinCurrencyClient);

        for (CryptoRateProviderChain exceptionalProvider:exceptionalProviders) {
            mainProvider.setNext(exceptionalProvider);
        }

        LOGGER.info("Kucoin provider was created with exceptional providers - {}", exceptionalProviders);

        return mainProvider;
    }

    @Override
    public void setExceptionalProviders(List<CryptoRateProviderChain> exceptionalProviders) {
        this.exceptionalProviders = exceptionalProviders;
    }
}
