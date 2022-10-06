package com.example.rateapi.client;

import com.example.rateapi.model.Crypto;
import com.example.rateapi.model.CryptoPriceInfo;
import com.example.rateapi.model.Currency;
import com.example.rateapi.service.logger.LoggerService;

import static java.lang.String.format;

public class CryptoCurrencyClientDecorator implements CryptoCurrencyClient {

    private final CryptoCurrencyClient cryptoCurrencyClient;

    private final LoggerService loggerService;

    public CryptoCurrencyClientDecorator(CryptoCurrencyClient cryptoCurrencyClient,
                                         LoggerService loggerService) {
        this.cryptoCurrencyClient = cryptoCurrencyClient;
        this.loggerService = loggerService;
        this.loggerService.setOutputClassName(this.getClass().getName());
    }

    @Override
    public CryptoPriceInfo getCryptoRateToLocalCurrency(Crypto crypto, Currency currency) {

        loggerService.logInfo(format("Making request to %s for crypto %s in currency %s (%s)", getApiUrl(), crypto.getFullName().toUpperCase(), currency, currency.getFullName()));
        CryptoPriceInfo cryptoPriceInfo = cryptoCurrencyClient.getCryptoRateToLocalCurrency(crypto, currency);
        loggerService.logDebug(format("Response from %s is %s", cryptoCurrencyClient, cryptoPriceInfo));
        return cryptoPriceInfo;
    }

    @Override
    public String getApiUrl() {
        return cryptoCurrencyClient.getApiUrl();
    }
}
