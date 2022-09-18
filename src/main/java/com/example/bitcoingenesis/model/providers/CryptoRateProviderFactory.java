package com.example.bitcoingenesis.model.providers;

import java.util.List;

public interface CryptoRateProviderFactory {

    CryptoRateProviderChain createProvider();

    void setExceptionalProviders(List<CryptoRateProviderChain> exceptionalProviders);

}
