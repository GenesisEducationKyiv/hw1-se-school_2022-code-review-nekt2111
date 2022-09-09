package com.example.bitcoingenesis.client;

import com.example.bitcoingenesis.model.CryptoPriceInfo;
import com.example.bitcoingenesis.model.Currency;
import com.example.bitcoingenesis.utill.CryptocurrencyShortPriceInfoDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.*;

@Service
@JsonDeserialize(using = CryptocurrencyShortPriceInfoDeserializer.class)
public class CoinGeckoCurrencyClient implements CryptoCurrencyClient {

    private final RestTemplate restTemplate;
    private final String coinGeckoSimpleApiUrl;

    public CoinGeckoCurrencyClient(RestTemplate restTemplate,
                                   @Value("${external.api.coingecko.simple.price}") String coinGeckoSimpleApiUrl) {
        this.restTemplate = restTemplate;
        this.coinGeckoSimpleApiUrl = coinGeckoSimpleApiUrl;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CoinGeckoCurrencyClient.class);

    @Override
    public Integer getCryptoRateToLocalCurrency(String cryptoCurrencyName, Currency currency) {
        LOGGER.info("Making request to {} for crypto {} in currency {} ({})", coinGeckoSimpleApiUrl, cryptoCurrencyName.toUpperCase(), currency, currency.getFullName());

        CryptoPriceInfo shortPriceInfo = restTemplate.getForEntity(UriComponentsBuilder
                .fromHttpUrl(coinGeckoSimpleApiUrl)
                .queryParam("ids", cryptoCurrencyName)
                .queryParam("vs_currencies", currency)
                .toUriString(), CryptoPriceInfo.class).getBody();

        LOGGER.info("Received data {} ", shortPriceInfo);

        return shortPriceInfo.getPrice();
    }

}
