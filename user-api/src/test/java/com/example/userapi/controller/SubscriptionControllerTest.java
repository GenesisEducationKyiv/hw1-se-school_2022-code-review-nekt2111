package com.example.userapi.controller;
import com.example.userapi.service.EmailValidationService;
import com.example.userapi.service.SubscriptionUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.example.userapi.TestConstants.EMAIL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SubscriptionControllerTest {

    private SubscriptionController subscriptionController;

    @Mock
    private SubscriptionUserService subscriptionEmailService;

    @Mock
    private EmailValidationService emailValidationService;

    @BeforeEach
    public void beforeTests() {
        subscriptionController = new SubscriptionController(subscriptionEmailService, emailValidationService);
    }

    @Test
    public void unsubscribeEmailNotFound() {
        when(subscriptionEmailService.isUserEmailSubscribed(EMAIL)).thenReturn(false);

        ResponseEntity<Void> response = subscriptionController.unsubscribe(EMAIL);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void subscriptions() {
        when(subscriptionEmailService.getAllSubscribedUsersEmails()).thenReturn(List.of(EMAIL));

        ResponseEntity<List<String>> subscribedEmails = subscriptionController.emailsOfSubscribers();

        assertNotNull(subscribedEmails.getBody());
        assertNotEquals(0, subscribedEmails.getBody().size());
        assertEquals(HttpStatus.OK, subscribedEmails.getStatusCode());
    }

    @Test
    public void subscribeNotValidated() {
        when(emailValidationService.validate(EMAIL)).thenReturn(false);

        ResponseEntity<Void> response = subscriptionController.subscribe(EMAIL);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void subscribeConflict() {
        when(emailValidationService.validate(EMAIL)).thenReturn(true);
        when(subscriptionEmailService.isUserEmailSubscribed(EMAIL)).thenReturn(true);

        ResponseEntity<Void> response = subscriptionController.subscribe(EMAIL);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void subscribeEmail() {
        when(emailValidationService.validate(EMAIL)).thenReturn(true);
        when(subscriptionEmailService.isUserEmailSubscribed(EMAIL)).thenReturn(false);

        ResponseEntity<Void> response = subscriptionController.subscribe(EMAIL);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}