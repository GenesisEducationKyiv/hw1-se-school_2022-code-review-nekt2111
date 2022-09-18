package com.example.bitcoingenesis.service.rate.providers;
import com.example.bitcoingenesis.service.rate.CryptoRateService;

public abstract class CryptoRateProviderChain implements CryptoRateService {

     public CryptoRateProviderChain next;
     public void setNext(CryptoRateProviderChain cryptoRateProviderChain) {
          this.next = cryptoRateProviderChain;
     };
}
