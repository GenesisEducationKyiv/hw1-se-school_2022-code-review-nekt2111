package com.example.bitcoingenesis.client;
import com.example.bitcoingenesis.model.Currency;

public interface CryptoCurrencyClient {

    Integer getCryptoRateToLocalCurrency(String cryptocurrencyName, Currency currency);

}
