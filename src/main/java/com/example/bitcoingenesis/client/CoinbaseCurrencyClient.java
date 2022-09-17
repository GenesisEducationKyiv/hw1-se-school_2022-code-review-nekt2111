package com.example.bitcoingenesis.client;

import com.example.bitcoingenesis.configuration.templates.CoinBaseRestTemplate;
import com.example.bitcoingenesis.model.Crypto;
import com.example.bitcoingenesis.model.Currency;
import com.example.bitcoingenesis.model.providers.coinbase.CoinBaseSpotPriceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CoinbaseCurrencyClient implements CryptoCurrencyClient{

    private final CoinBaseRestTemplate coinBaseRestTemplate;
    private final String coinbaseSpotPriceApiUrl;

    private static final Logger LOGGER = LoggerFactory.getLogger(CoinbaseCurrencyClient.class);

    public CoinbaseCurrencyClient(CoinBaseRestTemplate coinBaseRestTemplate,
                                   @Value("${external.api.coinbase.spot.price}") String coinbaseSpotPriceApiUrl) {
        this.coinBaseRestTemplate = coinBaseRestTemplate;
        this.coinbaseSpotPriceApiUrl = coinbaseSpotPriceApiUrl;
    }

    @Override
    public Integer getCryptoRateToLocalCurrency(Crypto crypto, Currency currency) {

        String sportPair = crypto.toString().toUpperCase() + "-" + currency.name().toUpperCase();

        String resultedUrl = coinbaseSpotPriceApiUrl.replace(":currency_pair", sportPair);

        LOGGER.info("Making request to {} for crypto {} in currency {} ({})", resultedUrl, crypto.getFullName().toUpperCase(), currency, currency.getFullName());

        CoinBaseSpotPriceResponse response = coinBaseRestTemplate.getForEntity(resultedUrl, CoinBaseSpotPriceResponse.class).getBody();

        LOGGER.info("Received data {} ", response);

        return response.getPrice().intValue();
    }
}
