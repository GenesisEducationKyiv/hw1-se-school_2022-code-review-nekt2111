package com.example.rateapi.service.providers;
import com.example.rateapi.service.CryptoRateService;

public abstract class CryptoRateProviderChain implements CryptoRateService {

     public CryptoRateProviderChain next;

     public void setNext(CryptoRateProviderChain cryptoRateProviderChain) {
          this.next = cryptoRateProviderChain;
     }
}
