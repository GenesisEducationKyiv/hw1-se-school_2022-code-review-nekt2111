package com.example.bitcoingenesis.controller;

import com.example.bitcoingenesis.client.CryptoCurrencyClient;
import com.example.bitcoingenesis.model.CryptoPriceInfo;
import com.example.bitcoingenesis.model.Currency;
import com.example.bitcoingenesis.repo.SubscriberEmailDao;
import com.example.bitcoingenesis.service.EmailService;
import com.example.bitcoingenesis.service.MessageService;
import org.springframework.beans.factory.annotation.Value;
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
    private final MessageService messageService;

    private final String email;

    public EmailController(EmailService emailService,
                           SubscriberEmailDao subscriberEmailDao,
                           CryptoCurrencyClient cryptoCurrencyClient,
                           MessageService messageService,
                           @Value("${spring.mail.username}") String email) {
        this.emailService = emailService;
        this.subscriberEmailDao = subscriberEmailDao;
        this.cryptoCurrencyClient = cryptoCurrencyClient;
        this.messageService = messageService;
        this.email = email;
    }

    @PostMapping
    public ResponseEntity<Void> sendEmails(@RequestParam(defaultValue = "bitcoin", required = false) String crypto,
                                     @RequestParam(defaultValue = "uah", required = false) String currency) {
        boolean wasResultOfSendingToAllSuccessful = sendEmailsWithRateOfCryptoInCurrency(crypto, Currency.valueOf(currency.toUpperCase()));

        if (!wasResultOfSendingToAllSuccessful) {
            return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean sendEmailsWithRateOfCryptoInCurrency(String crypto, Currency currency) {
        List<String> emails = subscriberEmailDao.findAll();

        Integer price = cryptoCurrencyClient.getCryptoRateToLocalCurrency(crypto, currency);
        CryptoPriceInfo cryptoPriceInfo = CryptoPriceInfo.createCryptoPriceInfo(crypto, currency, price);

        SimpleMailMessage mailMessage = messageService.createPriceMessageFromCryptoPriceInfo(cryptoPriceInfo, email);
        return emailService.sendEmailToAll(mailMessage, emails);
    }
}
