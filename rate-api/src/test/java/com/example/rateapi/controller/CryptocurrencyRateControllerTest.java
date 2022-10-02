package com.example.rateapi.controller;

import com.example.rateapi.model.Currency;
import com.example.rateapi.service.CryptoRateService;
import com.example.rateapi.service.logger.LoggerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static com.example.rateapi.TestConstants.CRYPTO;
import static com.example.rateapi.TestConstants.PRICE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class CryptocurrencyRateControllerTest {

    @DynamicPropertySource
    static void registerProperty(DynamicPropertyRegistry registry) {
        registry.add("crypto.rate.provider", () -> "coinbase");
    }

    private CryptocurrencyRateController cryptocurrencyRateController;

    @Mock
    private CryptoRateService cryptoRateService;

    @Mock
    private AmqpTemplate amqpTemplate;

    @Mock
    private LoggerService loggerService;

    @BeforeEach
    public void beforeTests() {
        cryptocurrencyRateController = new CryptocurrencyRateController(cryptoRateService, amqpTemplate, loggerService);
    }

    public void getRate() {
        Currency defaultCurrency = Currency.UAH;
        when(cryptoRateService.getCryptoRateToLocalCurrency(CRYPTO, defaultCurrency)).thenReturn(PRICE);

        ResponseEntity<Integer> response = cryptocurrencyRateController.rate();

        assertNotNull(response.getBody());
        assertEquals(PRICE, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    public void getRateForCryptocurrency() {
        String currency = Currency.USD.toString();
        when(cryptoRateService.getCryptoRateToLocalCurrency(CRYPTO, Currency.USD)).thenReturn(PRICE);

        ResponseEntity<Integer> response = cryptocurrencyRateController.rateForCurrency(CRYPTO.getFullName(), currency);

        assertNotNull(response.getBody());
        assertEquals(PRICE, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}