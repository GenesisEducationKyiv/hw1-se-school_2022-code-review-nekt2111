package com.example.bitcoingenesis.controllers;

import com.example.bitcoingenesis.controller.SubscriptionController;
import com.example.bitcoingenesis.service.SubscriptionEmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.example.bitcoingenesis.util.TestConstants.EMAIL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SubscriptionControllerTest {

    private SubscriptionController subscriptionController;

    @Mock
    private SubscriptionEmailService subscriptionEmailService;

    @BeforeEach
    public void beforeTests() {
        subscriptionController = new SubscriptionController(subscriptionEmailService);
    }

    @Test
    public void unsubscribeEmailNotFound() {
        when(subscriptionEmailService.isSubscribed(EMAIL)).thenReturn(false);

        ResponseEntity<Void> response = subscriptionController.unsubscribe(EMAIL);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void subscriptions() {
        when(subscriptionEmailService.getAllSubscribedEmails()).thenReturn(List.of(EMAIL));

        ResponseEntity<List<String>> subscribedEmails = subscriptionController.subscriptions();

        assertNotNull(subscribedEmails.getBody());
        assertNotEquals(0, subscribedEmails.getBody().size());
        assertEquals(HttpStatus.OK, subscribedEmails.getStatusCode());
    }

}

