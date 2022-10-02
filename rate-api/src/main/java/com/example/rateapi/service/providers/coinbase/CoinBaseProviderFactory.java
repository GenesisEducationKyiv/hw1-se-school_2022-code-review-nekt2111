package com.example.rateapi.service.providers.coinbase;

import com.example.rateapi.client.CoinbaseCurrencyClient;
import com.example.rateapi.service.logger.LoggerService;
import com.example.rateapi.service.providers.CryptoRateProviderChain;
import com.example.rateapi.service.providers.CryptoRateProviderFactory;
import org.springframework.stereotype.Component;

@Component
public class CoinBaseProviderFactory implements CryptoRateProviderFactory {

    private final CoinbaseCurrencyClient coinbaseCurrencyClient;

    private CryptoRateProviderChain exceptionalCryptoRateChain;

    private final LoggerService loggerService;

    public CoinBaseProviderFactory(CoinbaseCurrencyClient coinbaseCurrencyClient,
                                   LoggerService loggerService) {
        this.coinbaseCurrencyClient = coinbaseCurrencyClient;
        this.loggerService = loggerService;
        this.loggerService.setOutputClassName(this.getClass().getName());
    }

    @Override
    public CryptoRateProviderChain createProvider() {
        CryptoRateProviderChain mainProvider = new CoinBaseProvider(coinbaseCurrencyClient, loggerService);
        mainProvider.next = exceptionalCryptoRateChain;
        return mainProvider;
    }

    @Override
    public void setCryptoProviderExceptionalChain(CryptoRateProviderChain cryptoProviderExceptionalChain) {
        exceptionalCryptoRateChain = cryptoProviderExceptionalChain;
    }
}
