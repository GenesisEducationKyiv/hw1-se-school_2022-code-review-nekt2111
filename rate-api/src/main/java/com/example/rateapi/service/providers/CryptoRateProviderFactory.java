package com.example.rateapi.service.providers;

public interface CryptoRateProviderFactory {

    CryptoRateProviderChain createProvider();

    void setCryptoProviderExceptionalChain(CryptoRateProviderChain cryptoProviderExceptionalChain);

}
