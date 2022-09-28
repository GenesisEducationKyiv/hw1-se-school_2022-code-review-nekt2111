package com.example.rateapi.client;

import com.example.rateapi.configuration.templates.KucoinRestTemplate;
import com.example.rateapi.model.Crypto;
import com.example.rateapi.model.CryptoPriceInfo;
import com.example.rateapi.model.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class KucoinCurrencyClient implements CryptoCurrencyClient {

    private final KucoinRestTemplate kucoinRestTemplate;

    private final String kucoinFiatPriceApiUrl;

    private static final Logger LOGGER = LoggerFactory.getLogger(KucoinCurrencyClient.class);

    public KucoinCurrencyClient(KucoinRestTemplate kucoinRestTemplate,
                                @Value("${external.api.kucoin.fiat.price}") String kucoinFiatPriceApiUrl) {
        this.kucoinRestTemplate = kucoinRestTemplate;
        this.kucoinFiatPriceApiUrl = kucoinFiatPriceApiUrl;
    }

    @Override
    public CryptoPriceInfo getCryptoRateToLocalCurrency(Crypto crypto, Currency currency) {
        CryptoPriceInfo shortPriceInfo = kucoinRestTemplate.getForEntity(UriComponentsBuilder
                .fromHttpUrl(kucoinFiatPriceApiUrl)
                .queryParam("base", currency)
                .queryParam("currencies", crypto)
                .toUriString(), CryptoPriceInfo.class).getBody();

        shortPriceInfo.setCurrency(currency);

        return shortPriceInfo;
    }

    @Override
    public String getApiUrl() {
        return kucoinFiatPriceApiUrl;
    }

    @Override
    public String toString() {
        return "Kucoin client";
    }
}
