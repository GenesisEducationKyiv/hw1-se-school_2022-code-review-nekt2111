package com.example.bitcoingenesis.client;

import com.example.bitcoingenesis.configuration.templates.CoinBaseRestTemplate;
import com.example.bitcoingenesis.model.Crypto;
import com.example.bitcoingenesis.model.CryptoPriceInfo;
import com.example.bitcoingenesis.model.Currency;
import com.example.bitcoingenesis.model.PriceInCurrency;
import com.example.bitcoingenesis.service.rate.providers.coinbase.CoinBaseSpotPriceResponse;
import org.checkerframework.checker.units.qual.C;
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
