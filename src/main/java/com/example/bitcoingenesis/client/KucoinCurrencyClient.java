package com.example.bitcoingenesis.client;

import com.example.bitcoingenesis.configuration.templates.KucoinRestTemplate;
import com.example.bitcoingenesis.model.Crypto;
import com.example.bitcoingenesis.model.CryptoPriceInfo;
import com.example.bitcoingenesis.model.Currency;
import com.example.bitcoingenesis.model.PriceInCurrency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class KucoinCurrencyClient implements CryptoCurrencyClient{

    private final KucoinRestTemplate kucoinRestTemplate;

    private final String kucoinFiatPriceApiUrl;

    private static final Logger LOGGER = LoggerFactory.getLogger(KucoinCurrencyClient.class);

    public KucoinCurrencyClient(KucoinRestTemplate kucoinRestTemplate,
                                @Value("${external.api.kucoin.fiat.price}") String kucoinFiatPriceApiUrl) {
        this.kucoinRestTemplate = kucoinRestTemplate;
        this.kucoinFiatPriceApiUrl = kucoinFiatPriceApiUrl;
    }

    @Override
    public Integer getCryptoRateToLocalCurrency(Crypto crypto, Currency currency) {

        LOGGER.info("Making request to {} for crypto {} in currency {} ({})", kucoinFiatPriceApiUrl, crypto.getFullName(), currency, currency.getFullName());

        CryptoPriceInfo shortPriceInfo = kucoinRestTemplate.getForEntity(UriComponentsBuilder
                .fromHttpUrl(kucoinFiatPriceApiUrl)
                .queryParam("base", currency)
                .queryParam("currencies", crypto)
                .toUriString(), CryptoPriceInfo.class).getBody();

        shortPriceInfo.setCurrency(currency);

        LOGGER.info("Received data {} ", shortPriceInfo);

        return shortPriceInfo.getPrice();
    }
}
