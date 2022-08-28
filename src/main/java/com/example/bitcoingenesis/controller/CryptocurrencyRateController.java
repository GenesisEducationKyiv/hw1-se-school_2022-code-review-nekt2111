package com.example.bitcoingenesis.controller;

import com.example.bitcoingenesis.client.CryptoCurrencyClient;
import com.example.bitcoingenesis.model.Currency;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping("/rate")
public class CryptocurrencyRateController {

    private final CryptoCurrencyClient cryptoCurrencyClient;

    public CryptocurrencyRateController(CryptoCurrencyClient cryptoCurrencyClient) {
        this.cryptoCurrencyClient = cryptoCurrencyClient;
    }

    private final Currency defaultCurrency = Currency.UAH;
    private final String defaultCurrencyStr = "uah";
    private final String defaultCryptoCurrency = "bitcoin";

    @GetMapping
    public ResponseEntity<Integer> rate() {
        return ResponseEntity.ok(cryptoCurrencyClient.getRateToLocalCurrency(defaultCryptoCurrency, defaultCurrency));
    }

    @GetMapping("/{cryptocurrency}")
    public ResponseEntity<Integer> rateForCurrency(@PathVariable String cryptocurrency,
                                                   @RequestParam(required = false, defaultValue = defaultCurrencyStr) String currency) {
        return ResponseEntity.ok(cryptoCurrencyClient.getRateToLocalCurrency(cryptocurrency, Currency.valueOf(currency.toUpperCase())));
    }
}
