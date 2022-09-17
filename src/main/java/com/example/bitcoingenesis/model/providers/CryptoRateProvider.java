package com.example.bitcoingenesis.model.providers;

import com.example.bitcoingenesis.model.Crypto;
import com.example.bitcoingenesis.model.Currency;

public abstract class CryptoRateProvider {

     public abstract Integer getCryptoRateToLocalCurrency(Crypto cryptocurrencyName, Currency currency);
}
