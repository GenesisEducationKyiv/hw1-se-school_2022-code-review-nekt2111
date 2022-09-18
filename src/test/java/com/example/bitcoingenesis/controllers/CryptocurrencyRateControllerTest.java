package com.example.bitcoingenesis.controllers;

import com.example.bitcoingenesis.controller.CryptocurrencyRateController;
import com.example.bitcoingenesis.model.Currency;
import com.example.bitcoingenesis.service.rate.CryptoRateServiceProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.example.bitcoingenesis.util.TestConstants.CRYPTO;
import static com.example.bitcoingenesis.util.TestConstants.PRICE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CryptocurrencyRateControllerTest {

    private CryptocurrencyRateController cryptocurrencyRateController;

    @Mock
    private CryptoRateServiceProxy cryptoRateService;

    @BeforeEach
    public void beforeTests() {
        cryptocurrencyRateController = new CryptocurrencyRateController(cryptoRateService);
    }

    @Test
    public void getRate() {
        Currency defaultCurrency = Currency.UAH;
        when(cryptoRateService.getCryptoRateToLocalCurrency(CRYPTO, defaultCurrency)).thenReturn(PRICE);

        ResponseEntity<Integer> response = cryptocurrencyRateController.rate();

        assertNotNull(response.getBody());
        assertEquals(PRICE, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getRateForCryptocurrency() {
        String currency = Currency.USD.toString();
        when(cryptoRateService.getCryptoRateToLocalCurrency(CRYPTO, Currency.USD)).thenReturn(PRICE);

        ResponseEntity<Integer> response = cryptocurrencyRateController.rateForCurrency(CRYPTO.getFullName(), currency);

        assertNotNull(response.getBody());
        assertEquals(PRICE, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
