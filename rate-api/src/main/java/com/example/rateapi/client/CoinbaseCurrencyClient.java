package com.example.rateapi.client;

import com.example.rateapi.configuration.templates.CoinBaseRestTemplate;
import com.example.rateapi.model.Crypto;
import com.example.rateapi.model.CryptoPriceInfo;
import com.example.rateapi.model.Currency;
import com.example.rateapi.model.PriceInCurrency;
import com.example.rateapi.service.providers.coinbase.CoinBaseSpotPriceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CoinbaseCurrencyClient implements CryptoCurrencyClient {

    private final CoinBaseRestTemplate coinBaseRestTemplate;
    private final String coinbaseSpotPriceApiUrl;

    private static final Logger LOGGER = LoggerFactory.getLogger(CoinbaseCurrencyClient.class);

    public CoinbaseCurrencyClient(CoinBaseRestTemplate coinBaseRestTemplate,
                                   @Value("${external.api.coinbase.spot.price}") String coinbaseSpotPriceApiUrl) {
        this.coinBaseRestTemplate = coinBaseRestTemplate;
        this.coinbaseSpotPriceApiUrl = coinbaseSpotPriceApiUrl;
    }

    @Override
    public CryptoPriceInfo getCryptoRateToLocalCurrency(Crypto crypto, Currency currency) {
        String sportPair = crypto.toString().toUpperCase() + "-" + currency.name().toUpperCase();

        String resultedUrl = coinbaseSpotPriceApiUrl.replace(":currency_pair", sportPair);

        CoinBaseSpotPriceResponse response = coinBaseRestTemplate.getForEntity(resultedUrl, CoinBaseSpotPriceResponse.class).getBody();
        CryptoPriceInfo cryptoPriceInfo = new CryptoPriceInfo();
        PriceInCurrency priceInCurrency = new PriceInCurrency();
        cryptoPriceInfo.setPriceInCurrency(priceInCurrency);

        cryptoPriceInfo.setCrypto(crypto);
        cryptoPriceInfo.setPrice(response.getPrice().intValue());
        cryptoPriceInfo.setCurrency(currency);

        return cryptoPriceInfo;
    }

    @Override
    public String getApiUrl() {
        return coinbaseSpotPriceApiUrl;
    }

    @Override
    public String toString() {
        return "Coinbase client";
    }
}
