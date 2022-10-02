package com.example.rateapi.service.providers.coingecko;

import com.example.rateapi.client.CoinGeckoCurrencyClient;
import com.example.rateapi.service.logger.LoggerService;
import com.example.rateapi.service.providers.CryptoRateProviderChain;
import com.example.rateapi.service.providers.CryptoRateProviderFactory;
import org.springframework.stereotype.Component;

@Component
public class CoinGeckoProviderFactory implements CryptoRateProviderFactory {

    private final CoinGeckoCurrencyClient coinGeckoCurrencyClient;

    private CryptoRateProviderChain exceptionalCryptoProviderChain;

    private LoggerService loggerService;

    public CoinGeckoProviderFactory(CoinGeckoCurrencyClient coinGeckoCurrencyClient,
                                    LoggerService loggerService) {
        this.coinGeckoCurrencyClient = coinGeckoCurrencyClient;
        this.loggerService = loggerService;
        this.loggerService.setOutputClassName(this.getClass().getName());
    }

    @Override
    public CryptoRateProviderChain createProvider() {
        CryptoRateProviderChain mainProvider = new CoinGeckoProvider(coinGeckoCurrencyClient, loggerService);
        mainProvider.next = exceptionalCryptoProviderChain;

        return mainProvider;
    }

    @Override
    public void setCryptoProviderExceptionalChain(CryptoRateProviderChain cryptoProviderExceptionalChain) {
        this.exceptionalCryptoProviderChain = cryptoProviderExceptionalChain;
    }

}
