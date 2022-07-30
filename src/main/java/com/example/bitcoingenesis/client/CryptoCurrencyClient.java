package com.example.bitcoingenesis.client;

import com.example.bitcoingenesis.model.CryptocurrencyShortPriceInfo;
import com.example.bitcoingenesis.model.Currency;

public interface CryptoCurrencyClient {

    Integer getRateToLocalCurrency(String cryptocurrencyName, Currency currency);

    CryptocurrencyShortPriceInfo getCryptoShortPriceInfo(String cryptocurrencyName, Currency currency);

}
