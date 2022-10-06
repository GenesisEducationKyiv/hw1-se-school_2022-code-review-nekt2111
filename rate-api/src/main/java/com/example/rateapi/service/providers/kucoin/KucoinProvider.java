package com.example.rateapi.service.providers.kucoin;

import com.example.rateapi.client.CryptoCurrencyClient;
import com.example.rateapi.client.CryptoCurrencyClientDecorator;
import com.example.rateapi.client.KucoinCurrencyClient;
import com.example.rateapi.model.Crypto;
import com.example.rateapi.model.Currency;
import com.example.rateapi.service.logger.LoggerService;
import com.example.rateapi.service.providers.CryptoRateProviderChain;

import static java.lang.String.format;

public class KucoinProvider extends CryptoRateProviderChain {

    private final CryptoCurrencyClient cryptoCurrencyClient;

    private final LoggerService loggerService;

    public KucoinProvider(KucoinCurrencyClient kucoinCurrencyClient,
                          LoggerService loggerService) {
        this.cryptoCurrencyClient = new CryptoCurrencyClientDecorator(kucoinCurrencyClient, loggerService);
        this.loggerService = loggerService;
        this.loggerService.setOutputClassName(this.getClass().getName());
    }

    @Override
    public Integer getCryptoRateToLocalCurrency(Crypto cryptocurrencyName, Currency currency) {

        try {
            return cryptoCurrencyClient.getCryptoRateToLocalCurrency(cryptocurrencyName, currency).getPrice();
        } catch (Exception e) {
            loggerService.logError(format("Error occurred while getting data from %s with error message - %s", this, e.getMessage()));
            loggerService.logInfo(format("Getting data from an exceptional provider - %s", next));
            return next.getCryptoRateToLocalCurrency(cryptocurrencyName, currency);
        }
    }

    @Override
    public String toString() {
        return "Kucoin Provider";
    }
}
