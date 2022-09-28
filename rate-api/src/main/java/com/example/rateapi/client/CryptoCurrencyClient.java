package com.example.rateapi.client;

import com.example.rateapi.model.Crypto;
import com.example.rateapi.model.CryptoPriceInfo;
import com.example.rateapi.model.Currency;

public interface CryptoCurrencyClient {

    CryptoPriceInfo getCryptoRateToLocalCurrency(Crypto crypto, Currency currency);

    String getApiUrl();

}
