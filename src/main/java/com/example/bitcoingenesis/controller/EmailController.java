package com.example.bitcoingenesis.controller;

import com.example.bitcoingenesis.model.Crypto;
import com.example.bitcoingenesis.model.CryptoPriceInfo;
import com.example.bitcoingenesis.model.Currency;
import com.example.bitcoingenesis.repo.SubscriberEmailRepository;
import com.example.bitcoingenesis.service.email.EmailService;
import com.example.bitcoingenesis.service.message.MessageService;
import com.example.bitcoingenesis.service.rate.CryptoRateService;
import com.example.bitcoingenesis.service.rate.CryptoRateServiceProxy;
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
    private final SubscriberEmailRepository subscriberEmailRepository;
    private final CryptoRateService cryptoRateService;
    private final MessageService messageService;

    private final String email;

    public EmailController(EmailService emailService,
                           SubscriberEmailRepository subscriberEmailRepository,
                           CryptoRateService cryptoRateService,
                           MessageService messageService,
                           @Value("${spring.mail.username}") String email) {
        this.emailService = emailService;
        this.subscriberEmailRepository = subscriberEmailRepository;
        this.cryptoRateService = cryptoRateService;
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
        List<String> emails = subscriberEmailRepository.findAll();

        Integer price = cryptoRateService.getCryptoRateToLocalCurrency(Crypto.fromFullName(crypto), currency);
        CryptoPriceInfo cryptoPriceInfo = CryptoPriceInfo.createCryptoPriceInfo(Crypto.fromFullName(crypto), currency, price);

        SimpleMailMessage mailMessage = messageService.createPriceMessageFromCryptoPriceInfo(cryptoPriceInfo, email);
        return emailService.sendEmailToAll(mailMessage, emails);
    }
}
