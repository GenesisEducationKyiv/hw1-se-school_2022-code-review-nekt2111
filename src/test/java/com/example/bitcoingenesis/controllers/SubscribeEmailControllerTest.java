package com.example.bitcoingenesis.controllers;

import com.example.bitcoingenesis.controller.SubscribeEmailController;
import com.example.bitcoingenesis.service.EmailValidationService;
import com.example.bitcoingenesis.service.SubscriptionEmailService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.example.bitcoingenesis.util.TestConstants.EMAIL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SubscribeEmailControllerTest {

    private SubscribeEmailController subscribeEmailController;

    @Mock
    private SubscriptionEmailService subscriptionEmailService;

    @Mock
    private EmailValidationService emailValidationService;

    @Before
    public void beforeTests() {
        this.subscribeEmailController = new SubscribeEmailController(subscriptionEmailService, emailValidationService);
    }

    @Test
    public void subscribeNotValidated() {
        when(emailValidationService.validate(EMAIL)).thenReturn(false);

        ResponseEntity<Void> response = subscribeEmailController.subscribe(EMAIL);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void subscribeConflict() {
        when(emailValidationService.validate(EMAIL)).thenReturn(true);
        when(subscriptionEmailService.isSubscribed(EMAIL)).thenReturn(true);

        ResponseEntity<Void> response = subscribeEmailController.subscribe(EMAIL);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void subscribeEmail() {
        when(emailValidationService.validate(EMAIL)).thenReturn(true);
        when(subscriptionEmailService.isSubscribed(EMAIL)).thenReturn(false);

        ResponseEntity<Void> response = subscribeEmailController.subscribe(EMAIL);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
