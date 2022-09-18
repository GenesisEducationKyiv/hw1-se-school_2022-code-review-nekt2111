package com.example.bitcoingenesis.service.rate.providers;

import java.util.List;

public interface CryptoRateProviderFactory {

    CryptoRateProviderChain createProvider();

    void setCryptoProviderExceptionalChain(CryptoRateProviderChain cryptoProviderExceptionalChain);

}