package com.example.rateapi.service.providers.coinbase;


import com.example.rateapi.client.CoinbaseCurrencyClient;
import com.example.rateapi.client.CryptoCurrencyClient;
import com.example.rateapi.client.CryptoCurrencyClientDecorator;
import com.example.rateapi.model.Crypto;
import com.example.rateapi.model.Currency;
import com.example.rateapi.service.logger.LoggerService;
import com.example.rateapi.service.providers.CryptoRateProviderChain;


import static java.lang.String.format;

public class CoinBaseProvider extends CryptoRateProviderChain {

    private final CryptoCurrencyClient cryptoCurrencyClient;

    private final LoggerService loggerService;

    public CoinBaseProvider(CoinbaseCurrencyClient coinbaseCurrencyClient,
                            LoggerService loggerService) {
        this.cryptoCurrencyClient = new CryptoCurrencyClientDecorator(coinbaseCurrencyClient, loggerService);
        this.loggerService = loggerService;
        this.loggerService.setOutputClassName(this.getClass().getName());
    }

    @Override
    public Integer getCryptoRateToLocalCurrency(Crypto crypto, Currency currency) {
        try {
            return cryptoCurrencyClient.getCryptoRateToLocalCurrency(crypto, currency).getPrice();
        } catch (Exception e) {
            loggerService.logError(format("Error occurred while getting data from %s with error message - %s", this, e.getMessage()));
            loggerService.logInfo(format("Getting data from an exceptional provider - %s", next));
            return next.getCryptoRateToLocalCurrency(crypto, currency);
        }
    }

    @Override
    public String toString() {
        return "CoinBase Provider";
    }
}
