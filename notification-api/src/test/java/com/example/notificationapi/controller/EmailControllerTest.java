package com.example.notificationapi.controller;

import com.example.notificationapi.client.CryptoRateClient;
import com.example.notificationapi.client.UserClient;
import com.example.notificationapi.model.rate.CryptoPriceInfo;
import com.example.notificationapi.service.email.EmailService;
import com.example.notificationapi.service.message.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import static com.example.notificationapi.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EmailControllerTest {

    private EmailController emailController;

    @Mock
    private EmailService emailService;

    @Mock
    private UserClient userClient;

    @Mock
    private CryptoRateClient cryptoRateClient;

    @Mock
    private MessageService messageService;

    @BeforeEach
    public void beforeTests() {
        emailController = new EmailController(emailService,
                messageService,
                userClient,
                cryptoRateClient,
                EMAIL);
    }

    @Test
    public void sendEmails() {

        CryptoPriceInfo cryptoPriceInfo = CryptoPriceInfo.createCryptoPriceInfo(CRYPTO, CURRENCY,PRICE);

        when(userClient.getAllUsersSubscribedEmails()).thenReturn(List.of(EMAIL));
        when(cryptoRateClient.getRateForCryptoInCurrency(CRYPTO, CURRENCY)).thenReturn(PRICE);
        when(messageService.createPriceMessageFromCryptoPriceInfo(cryptoPriceInfo, EMAIL)).thenReturn(SIMPLE_MAIL_MESSAGE);
        when(emailService.sendEmailToAll(SIMPLE_MAIL_MESSAGE, List.of(EMAIL))).thenReturn(true);

        ResponseEntity<Void> response = emailController.sendEmails(CRYPTO.getFullName(), CURRENCY.toString());

        verify(emailService).sendEmailToAll(SIMPLE_MAIL_MESSAGE, List.of(EMAIL));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void sendEmailsFailed() {

        CryptoPriceInfo cryptoPriceInfo = CryptoPriceInfo.createCryptoPriceInfo(CRYPTO, CURRENCY,PRICE);

        when(userClient.getAllUsersSubscribedEmails()).thenReturn(List.of(EMAIL));
        when(cryptoRateClient.getRateForCryptoInCurrency(CRYPTO, CURRENCY)).thenReturn(PRICE);
        when(messageService.createPriceMessageFromCryptoPriceInfo(cryptoPriceInfo, EMAIL)).thenReturn(SIMPLE_MAIL_MESSAGE);
        when(emailService.sendEmailToAll(SIMPLE_MAIL_MESSAGE, List.of(EMAIL))).thenReturn(false);

        ResponseEntity<Void> response = emailController.sendEmails(CRYPTO.getFullName(), CURRENCY.toString());

        verify(emailService).sendEmailToAll(SIMPLE_MAIL_MESSAGE, List.of(EMAIL));
        assertEquals(HttpStatus.FAILED_DEPENDENCY, response.getStatusCode());
    }

}