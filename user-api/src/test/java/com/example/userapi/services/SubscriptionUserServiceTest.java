package com.example.userapi.services;

import com.example.userapi.repo.SubscriberUserRepository;
import com.example.userapi.service.SubscriptionUserService;
import com.example.userapi.service.SubscriptionUserServiceImpl;
import com.example.userapi.service.logger.LoggerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.example.userapi.TestConstants.EMAIL;
import static com.example.userapi.TestConstants.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SubscriptionUserServiceTest {

    private SubscriptionUserService subscriptionUserService;

    @Mock
    private SubscriberUserRepository subscriberUserRepository;

    @Mock
    private LoggerService loggerService;

    @BeforeEach
    public void beforeTests() {
        this.subscriptionUserService = new SubscriptionUserServiceImpl(subscriberUserRepository, loggerService);
    }

    @Test
    public void subscribe() {
        subscriptionUserService.subscribe(USER);

        verify(subscriberUserRepository).insert(USER);
    }

    @Test
    public void unsubscribe() {
        subscriptionUserService.unsubscribe(USER);

        verify(subscriberUserRepository).delete(USER);
    }


    @Test
    public void getAllSubscribedEmails() {
        when(subscriberUserRepository.findAll()).thenReturn(List.of(USER));

        List<String> subscribedEmails = subscriptionUserService.getAllSubscribedUsersEmails();

        assertNotEquals(0, subscribedEmails.size());
    }

    @Test
    public void isSubscribedTrue() {
        when(subscriberUserRepository.findByEmail(EMAIL)).thenReturn(USER);

        boolean isSubscribed = subscriptionUserService.isUserEmailSubscribed(EMAIL);

        assertTrue(isSubscribed);
    }

    @Test
    public void isSubscribedFalse() {
        when(subscriberUserRepository.findByEmail(EMAIL)).thenReturn(null);

        boolean isSubscribed = subscriptionUserService.isUserEmailSubscribed(EMAIL);

        assertFalse(isSubscribed);
    }

}
