package com.example.rateapi.service.providers.kucoin;

import com.example.rateapi.client.KucoinCurrencyClient;
import com.example.rateapi.service.providers.CryptoRateProviderChain;
import com.example.rateapi.service.providers.CryptoRateProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class KucoinProviderFactory implements CryptoRateProviderFactory {

    private final KucoinCurrencyClient kucoinCurrencyClient;

    private CryptoRateProviderChain exceptionalCryptoProviderChain;

    private static final Logger LOGGER = LoggerFactory.getLogger(KucoinProviderFactory.class);

    public KucoinProviderFactory(KucoinCurrencyClient kucoinCurrencyClient) {
        this.kucoinCurrencyClient = kucoinCurrencyClient;
    }

    @Override
    public CryptoRateProviderChain createProvider() {
        CryptoRateProviderChain mainProvider = new KucoinProvider(kucoinCurrencyClient);
        mainProvider.next = exceptionalCryptoProviderChain;
        return mainProvider;
    }

    @Override
    public void setCryptoProviderExceptionalChain(CryptoRateProviderChain cryptoProviderExceptionalChain) {
        exceptionalCryptoProviderChain = cryptoProviderExceptionalChain;
    }
}
