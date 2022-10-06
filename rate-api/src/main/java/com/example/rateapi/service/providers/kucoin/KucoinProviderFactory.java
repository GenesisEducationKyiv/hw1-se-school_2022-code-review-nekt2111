package com.example.rateapi.service.providers.kucoin;

import com.example.rateapi.client.KucoinCurrencyClient;
import com.example.rateapi.service.logger.LoggerService;
import com.example.rateapi.service.providers.CryptoRateProviderChain;
import com.example.rateapi.service.providers.CryptoRateProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class KucoinProviderFactory implements CryptoRateProviderFactory {

    private final KucoinCurrencyClient kucoinCurrencyClient;

    private CryptoRateProviderChain exceptionalCryptoProviderChain;

    private LoggerService loggerService;

    private static final Logger LOGGER = LoggerFactory.getLogger(KucoinProviderFactory.class);

    public KucoinProviderFactory(KucoinCurrencyClient kucoinCurrencyClient,
                                 LoggerService loggerService) {
        this.kucoinCurrencyClient = kucoinCurrencyClient;
        this.loggerService = loggerService;
        this.loggerService.setOutputClassName(this.getClass().getName());
    }

    @Override
    public CryptoRateProviderChain createProvider() {
        CryptoRateProviderChain mainProvider = new KucoinProvider(kucoinCurrencyClient, loggerService);
        mainProvider.next = exceptionalCryptoProviderChain;
        return mainProvider;
    }

    @Override
    public void setCryptoProviderExceptionalChain(CryptoRateProviderChain cryptoProviderExceptionalChain) {
        exceptionalCryptoProviderChain = cryptoProviderExceptionalChain;
    }
}
