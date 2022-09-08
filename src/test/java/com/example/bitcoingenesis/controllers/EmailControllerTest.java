package com.example.bitcoingenesis.controllers;

import com.example.bitcoingenesis.client.CryptoCurrencyClient;
import com.example.bitcoingenesis.controller.EmailController;
import com.example.bitcoingenesis.model.CryptoPriceInfo;
import com.example.bitcoingenesis.repo.SubscriberEmailDao;
import com.example.bitcoingenesis.service.EmailService;
import com.example.bitcoingenesis.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.example.bitcoingenesis.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EmailControllerTest {

    private EmailController emailController;

    @Mock
    private EmailService emailService;

    @Mock
    private SubscriberEmailDao subscriberEmailDao;

    @Mock
    private CryptoCurrencyClient cryptoCurrencyClient;

    @Mock
    private MessageService messageService;

    @BeforeEach
    public void beforeTests() {
        emailController = new EmailController(emailService,
                subscriberEmailDao,
                cryptoCurrencyClient,
                messageService,
                EMAIL);
    }

    @Test
    public void sendEmails() {
        when(subscriberEmailDao.findAll()).thenReturn(List.of(EMAIL));
        when(cryptoCurrencyClient.getCryptoRateToLocalCurrency(CRYPTO, CURRENCY)).thenReturn(PRICE);
        when(messageService.createPriceMessageFromCryptoPriceInfo(CryptoPriceInfo.createCryptoPriceInfo(CRYPTO, CURRENCY,PRICE), EMAIL));

        ResponseEntity<Void> response = emailController.sendEmails(CRYPTO, CURRENCY.toString());

        verify(emailService).sendEmailToAll(SIMPLE_MAIL_MESSAGE, List.of(EMAIL));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
