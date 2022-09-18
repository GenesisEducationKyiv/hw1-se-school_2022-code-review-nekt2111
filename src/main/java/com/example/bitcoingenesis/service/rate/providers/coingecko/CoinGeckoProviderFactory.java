package com.example.bitcoingenesis.service.rate.providers.coingecko;

import com.example.bitcoingenesis.client.CoinGeckoCurrencyClient;
import com.example.bitcoingenesis.service.rate.providers.CryptoRateProviderChain;
import com.example.bitcoingenesis.service.rate.providers.CryptoRateProviderFactory;
import com.example.bitcoingenesis.service.rate.providers.coinbase.CoinBaseProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CoinGeckoProviderFactory implements CryptoRateProviderFactory {

    private final CoinGeckoCurrencyClient coinGeckoCurrencyClient;

    private CryptoRateProviderChain exceptionalCryptoProviderChain;

    private static final Logger LOGGER = LoggerFactory.getLogger(CoinBaseProviderFactory.class);

    public CoinGeckoProviderFactory(CoinGeckoCurrencyClient coinGeckoCurrencyClient) {
        this.coinGeckoCurrencyClient = coinGeckoCurrencyClient;

    }

    @Override
    public CryptoRateProviderChain createProvider() {
        CryptoRateProviderChain mainProvider = new CoinGeckoProvider(coinGeckoCurrencyClient);
        mainProvider.next = exceptionalCryptoProviderChain;

        return mainProvider;
    }

    @Override
    public void setCryptoProviderExceptionalChain(CryptoRateProviderChain cryptoProviderExceptionalChain) {
        this.exceptionalCryptoProviderChain = cryptoProviderExceptionalChain;
    }

}
