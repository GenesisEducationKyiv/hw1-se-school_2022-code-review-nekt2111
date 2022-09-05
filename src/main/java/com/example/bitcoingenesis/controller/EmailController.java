package com.example.bitcoingenesis.controller;

import com.example.bitcoingenesis.client.CryptoCurrencyClient;
import com.example.bitcoingenesis.model.CryptocurrencyShortPriceInfo;
import com.example.bitcoingenesis.model.Currency;
import com.example.bitcoingenesis.repo.SubscriberEmailDao;
import com.example.bitcoingenesis.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sendEmails")
public class EmailController {
    private final EmailService emailService;
    private final SubscriberEmailDao subscriberEmailDao;
    private final CryptoCurrencyClient cryptoCurrencyClient;

    public EmailController(EmailService emailService,
                           SubscriberEmailDao subscriberEmailDao,
                           CryptoCurrencyClient cryptoCurrencyClient) {
        this.emailService = emailService;
        this.subscriberEmailDao = subscriberEmailDao;
        this.cryptoCurrencyClient = cryptoCurrencyClient;
    }

    @PostMapping
    public ResponseEntity<Void> sendEmails(@RequestParam(defaultValue = "bitcoin", required = false) String crypto,
                                     @RequestParam(defaultValue = "uah", required = false) String currency) {
        sendEmailsWithRateOfCryptoInCurrency(crypto, Currency.valueOf(currency.toUpperCase()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void sendEmailsWithRateOfCryptoInCurrency(String crypto, Currency currency) {
        List<String> emails = subscriberEmailDao.findAll();

        CryptocurrencyShortPriceInfo cryptoPriceInfo = cryptoCurrencyClient.getCryptoShortPriceInfo(crypto, currency);
        SimpleMailMessage mailMessage = emailService.createMessageFromCryptocurrencyShortPriceInfo(cryptoPriceInfo);
        emailService.sendEmailToAll(mailMessage, emails);

    }
}
