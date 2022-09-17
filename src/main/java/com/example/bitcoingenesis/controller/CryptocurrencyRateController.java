package com.example.bitcoingenesis.controller;

import com.example.bitcoingenesis.client.CryptoCurrencyClient;
import com.example.bitcoingenesis.model.Crypto;
import com.example.bitcoingenesis.model.Currency;
import com.example.bitcoingenesis.service.rate.CryptoRateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping("/rate")
public class CryptocurrencyRateController {

    private final CryptoRateService cryptoRateService;

    public CryptocurrencyRateController(CryptoRateService cryptoRateService) {
        this.cryptoRateService = cryptoRateService;
    }

    private final Currency defaultCurrency = Currency.UAH;
    private final String defaultCurrencyStr = "uah";
    private final String defaultCryptoCurrency = "bitcoin";

    @GetMapping
    public ResponseEntity<Integer> rate() {
        return ResponseEntity.ok(cryptoRateService.getCryptoRateToLocalCurrency(Crypto.fromFullName(defaultCryptoCurrency), defaultCurrency));
    }

    @GetMapping("/{cryptocurrency}")
    public ResponseEntity<Integer> rateForCurrency(@PathVariable String cryptocurrency,
                                                   @RequestParam(required = false, defaultValue = defaultCurrencyStr) String currency) {
        return ResponseEntity.ok(cryptoRateService.getCryptoRateToLocalCurrency(Crypto.fromFullName(cryptocurrency), Currency.valueOf(currency.toUpperCase())));
    }
}
