package com.example.notificationapi.client;

import com.example.notificationapi.model.rate.Crypto;
import com.example.notificationapi.model.rate.Currency;

public interface CryptoRateClient {

    Integer getRate();

    Integer getRateForCryptoInCurrency(Crypto crypto, Currency currency);
}
