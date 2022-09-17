package com.example.bitcoingenesis.client;

import com.example.bitcoingenesis.configuration.templates.CoinGeckoRestTemplate;
import com.example.bitcoingenesis.model.Crypto;
import com.example.bitcoingenesis.model.CryptoPriceInfo;
import com.example.bitcoingenesis.model.Currency;
import com.example.bitcoingenesis.model.providers.coingecko.CoinGeckoSimplePriceDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.*;

@Service
public class CoinGeckoCurrencyClient implements CryptoCurrencyClient {

    private final CoinGeckoRestTemplate coinGeckoRestTemplate;
    private final String coinGeckoSimpleApiUrl;

    public CoinGeckoCurrencyClient(CoinGeckoRestTemplate coinGeckoRestTemplate,
                                   @Value("${external.api.coingecko.simple.price}") String coinGeckoSimpleApiUrl) {
        this.coinGeckoRestTemplate = coinGeckoRestTemplate;
        this.coinGeckoSimpleApiUrl = coinGeckoSimpleApiUrl;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CoinGeckoCurrencyClient.class);

    @Override
    public Integer getCryptoRateToLocalCurrency(Crypto crypto, Currency currency) {
        LOGGER.info("Making request to {} for crypto {} in currency {} ({})", coinGeckoSimpleApiUrl, crypto.getFullName(), currency, currency.getFullName());

        CryptoPriceInfo shortPriceInfo = coinGeckoRestTemplate.getForEntity(UriComponentsBuilder
                .fromHttpUrl(coinGeckoSimpleApiUrl)
                .queryParam("ids", crypto.getFullName())
                .queryParam("vs_currencies", currency)
                .toUriString(), CryptoPriceInfo.class).getBody();

        LOGGER.info("Received data {} ", shortPriceInfo);

        return shortPriceInfo.getPrice();
    }

}
