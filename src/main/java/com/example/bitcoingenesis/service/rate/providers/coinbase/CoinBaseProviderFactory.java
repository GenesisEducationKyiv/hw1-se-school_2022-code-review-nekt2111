package com.example.bitcoingenesis.service.rate.providers.coinbase;

import com.example.bitcoingenesis.client.CoinbaseCurrencyClient;
import com.example.bitcoingenesis.service.rate.providers.CryptoRateProviderChain;
import com.example.bitcoingenesis.service.rate.providers.CryptoRateProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CoinBaseProviderFactory implements CryptoRateProviderFactory {

    private final CoinbaseCurrencyClient coinbaseCurrencyClient;

    private CryptoRateProviderChain exceptionalCryptoRateChain;

    private static final Logger LOGGER = LoggerFactory.getLogger(CoinBaseProviderFactory.class);

    public CoinBaseProviderFactory(CoinbaseCurrencyClient coinbaseCurrencyClient) {
        this.coinbaseCurrencyClient = coinbaseCurrencyClient;
    }

    @Override
    public CryptoRateProviderChain createProvider() {
        CryptoRateProviderChain mainProvider = new CoinBaseProvider(coinbaseCurrencyClient);
        mainProvider.next = exceptionalCryptoRateChain;
        return mainProvider;
    }

    @Override
    public void setCryptoProviderExceptionalChain(CryptoRateProviderChain cryptoProviderExceptionalChain) {
        exceptionalCryptoRateChain = cryptoProviderExceptionalChain;
    }
}
