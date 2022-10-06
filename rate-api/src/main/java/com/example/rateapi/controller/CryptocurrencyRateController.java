package com.example.rateapi.controller;

import com.example.rateapi.model.Crypto;
import com.example.rateapi.model.Currency;
import com.example.rateapi.service.CryptoRateService;
import com.example.rateapi.service.logger.LoggerService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping("/rate")
public class CryptocurrencyRateController {

    private final CryptoRateService cryptoRateService;

    private final LoggerService loggerService;

    public CryptocurrencyRateController(CryptoRateService cryptoRateService,
                                        AmqpTemplate amqpTemplate,
                                        LoggerService loggerService) {
        this.cryptoRateService = cryptoRateService;
        this.loggerService = loggerService;
        loggerService.setOutputClassName(this.getClass().getName());
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
