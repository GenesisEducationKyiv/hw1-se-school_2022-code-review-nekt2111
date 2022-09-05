package com.example.bitcoingenesis.controllers;

import com.example.bitcoingenesis.client.CryptoCurrencyClient;
import com.example.bitcoingenesis.controller.CryptocurrencyRateController;
import com.example.bitcoingenesis.model.Currency;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.example.bitcoingenesis.util.TestConstants.PRICE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CryptocurrencyRateControllerTest {

    private CryptocurrencyRateController cryptocurrencyRateController;

    @Mock
    private CryptoCurrencyClient cryptoCurrencyClient;

    @Before
    public void beforeTests() {
        cryptocurrencyRateController = new CryptocurrencyRateController(cryptoCurrencyClient);
    }

    @Test
    public void getRate() {
        Currency defaultCurrency = Currency.UAH;
        String defaultCrypto = "bitcoin";
        when(cryptoCurrencyClient.getRateToLocalCurrency(defaultCrypto, defaultCurrency)).thenReturn(PRICE);

        ResponseEntity<Integer> response = cryptocurrencyRateController.rate();

        assertNotNull(response.getBody());
        assertEquals(PRICE, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getRateForCryptocurrency() {
        String currency = Currency.USD.toString();
        String crypto = "ethereum";
        when(cryptoCurrencyClient.getRateToLocalCurrency(crypto, Currency.USD)).thenReturn(PRICE);

        ResponseEntity<Integer> response = cryptocurrencyRateController.rateForCurrency(crypto, currency);

        assertNotNull(response.getBody());
        assertEquals(PRICE, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}