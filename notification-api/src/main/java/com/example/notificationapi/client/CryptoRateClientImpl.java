package com.example.notificationapi.client;

import com.example.notificationapi.model.rate.Crypto;
import com.example.notificationapi.model.rate.Currency;
import com.example.notificationapi.service.logger.LoggerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static java.lang.String.format;

@Component
public class CryptoRateClientImpl implements CryptoRateClient {

    private final String rateApiUrl;

    private final RestTemplate restTemplate;

    private LoggerService loggerService;

    public CryptoRateClientImpl(@Value("${bitcoin.genesis.rate.api.url}") String rateApiUrl,
                                RestTemplate restTemplate,
                                LoggerService loggerService) {
        this.rateApiUrl = rateApiUrl;
        this.restTemplate = restTemplate;
        this.loggerService = loggerService;
        this.loggerService.setOutputClassName(this.getClass().getName());
    }

    @Override
    public Integer getRate() {
        String uri = rateApiUrl + "/rate";
        loggerService.logInfo(format("Making request on %s",uri));
        ResponseEntity<Integer> response = restTemplate.getForEntity(rateApiUrl + "/rate", Integer.class);
        loggerService.logDebug(format("Received response - %s", response));
        return response.getBody();
    }

    @Override
    public Integer getRateForCryptoInCurrency(Crypto crypto, Currency currency) {

        String uri = UriComponentsBuilder.fromHttpUrl(rateApiUrl + "/rate")
                .pathSegment( crypto.getFullName())
                .queryParam("currency", currency.toString())
                .toUriString();

        loggerService.logInfo(format("Making request on %s to get rate for %s in %s",uri, crypto.getFullName(), currency));

        ResponseEntity<Integer> response = restTemplate.getForEntity(uri, Integer.class);

        loggerService.logError(format("Received response - %s", response));

        return response.getBody();
    }
}
