package com.example.bitcoingenesis.model.providers;

import com.example.bitcoingenesis.model.Crypto;
import com.example.bitcoingenesis.model.Currency;

import java.util.List;

public abstract class CryptoRateProviderChain {

     public CryptoRateProviderChain next;

     public abstract Integer getCryptoRateToLocalCurrency(Crypto cryptocurrencyName, Currency currency);
     public void setNext(CryptoRateProviderChain cryptoRateProviderChain) {
          this.next = cryptoRateProviderChain;
     };
}
