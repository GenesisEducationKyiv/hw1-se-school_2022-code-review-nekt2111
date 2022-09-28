package com.example.notificationapi.controller;

import com.example.notificationapi.client.CryptoRateClient;
import com.example.notificationapi.client.UserClient;
import com.example.notificationapi.model.rate.Crypto;
import com.example.notificationapi.model.rate.CryptoPriceInfo;
import com.example.notificationapi.model.rate.Currency;
import com.example.notificationapi.service.email.EmailService;
import com.example.notificationapi.service.message.MessageService;
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

    private final MessageService messageService;

    private final UserClient userClient;

    private final CryptoRateClient cryptoRateClient;

    private final String email;

    public EmailController(EmailService emailService,
                           MessageService messageService,
                           UserClient userClient, CryptoRateClient cryptoRateClient, @Value("${spring.mail.username}") String email) {
        this.emailService = emailService;
        this.messageService = messageService;
        this.userClient = userClient;
        this.cryptoRateClient = cryptoRateClient;
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
        List<String> emails = userClient.getAllUsersSubscribedEmails();

        Integer price = cryptoRateClient.getRateForCryptoInCurrency(Crypto.fromFullName(crypto), currency);
        CryptoPriceInfo cryptoPriceInfo = CryptoPriceInfo.createCryptoPriceInfo(Crypto.fromFullName(crypto), currency, price);

        SimpleMailMessage mailMessage = messageService.createPriceMessageFromCryptoPriceInfo(cryptoPriceInfo, email);
        return emailService.sendEmailToAll(mailMessage, emails);
    }
}
