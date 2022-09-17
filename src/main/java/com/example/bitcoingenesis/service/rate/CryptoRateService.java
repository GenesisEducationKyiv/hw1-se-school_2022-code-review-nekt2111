package com.example.bitcoingenesis.service.rate;

import com.example.bitcoingenesis.model.Crypto;
import com.example.bitcoingenesis.model.Currency;
import com.example.bitcoingenesis.model.providers.CryptoRateProvider;
import com.example.bitcoingenesis.model.providers.CryptoRateProviderFactory;
import org.springframework.stereotype.Service;

@Service
public class CryptoRateService {

    private final CryptoRateProvider cryptoRateProvider;

    public CryptoRateService(CryptoRateProviderFactory cryptoRateProviderFactory) {
        cryptoRateProvider = cryptoRateProviderFactory.createProvider();
    }

    public Integer getCryptoRateToLocalCurrency(Crypto crypto, Currency currency) {
        return cryptoRateProvider.getCryptoRateToLocalCurrency(crypto, currency);
    }

}
