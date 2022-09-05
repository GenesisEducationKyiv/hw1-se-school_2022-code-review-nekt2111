package com.example.bitcoingenesis.controllers;

import com.example.bitcoingenesis.client.CryptoCurrencyClient;
import com.example.bitcoingenesis.controller.EmailController;
import com.example.bitcoingenesis.repo.SubscriberEmailDao;
import com.example.bitcoingenesis.service.EmailService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.example.bitcoingenesis.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmailControllerTest {

    private EmailController emailController;

    @Mock
    private EmailService emailService;

    @Mock
    private SubscriberEmailDao subscriberEmailDao;

    @Mock
    private CryptoCurrencyClient cryptoCurrencyClient;

    @Before
    public void beforeTests() {
        emailController = new EmailController(emailService, subscriberEmailDao, cryptoCurrencyClient);
    }

    @Test
    public void sendEmails() {
        when(subscriberEmailDao.findAll()).thenReturn(List.of(EMAIL));
        when(cryptoCurrencyClient.getCryptoShortPriceInfo(CRYPTO, CURRENCY)).thenReturn(SHORT_PRICE_INFO);
        when(emailService.createMessageFromCryptocurrencyShortPriceInfo(SHORT_PRICE_INFO)).thenReturn(SIMPLE_MAIL_MESSAGE);

        ResponseEntity<Void> response = emailController.sendEmails(CRYPTO, CURRENCY.toString());

        verify(emailService).sendEmailToAll(SIMPLE_MAIL_MESSAGE, List.of(EMAIL));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}