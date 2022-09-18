package com.example.bitcoingenesis.service.rate;

import com.example.bitcoingenesis.model.Crypto;
import com.example.bitcoingenesis.model.Currency;
import com.example.bitcoingenesis.model.providers.CryptoRateProviderChain;
import com.example.bitcoingenesis.model.providers.CryptoRateProviderFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CryptoRateService {

    private final CryptoRateProviderChain cryptoRateProviderChain;

    public CryptoRateService(CryptoRateProviderFactory cryptoRateProviderFactory) {
        cryptoRateProviderChain = cryptoRateProviderFactory.createProvider();
    }

    public Integer getCryptoRateToLocalCurrency(Crypto crypto, Currency currency) {
        return cryptoRateProviderChain.getCryptoRateToLocalCurrency(crypto, currency);
    }

}
