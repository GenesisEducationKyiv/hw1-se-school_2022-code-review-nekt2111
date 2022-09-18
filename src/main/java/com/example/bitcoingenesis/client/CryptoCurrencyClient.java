package com.example.bitcoingenesis.client;
import com.example.bitcoingenesis.model.Crypto;
import com.example.bitcoingenesis.model.CryptoPriceInfo;
import com.example.bitcoingenesis.model.Currency;

public interface CryptoCurrencyClient {

    CryptoPriceInfo getCryptoRateToLocalCurrency(Crypto crypto, Currency currency);

    String getApiUrl();

}
