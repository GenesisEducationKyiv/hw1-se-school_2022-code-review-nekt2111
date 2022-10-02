package com.example.notificationapi.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class UserClientImpl implements UserClient {

    private final String userApiUrl;
    private final RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserClient.class);


    public UserClientImpl(@Value("${bitcoin.genesis.user.api.url}") String userApiUrl,
                          RestTemplate restTemplate) {
        this.userApiUrl = userApiUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<String> getAllUsersSubscribedEmails() {
        String uri = userApiUrl + "/subscription/emails";
        LOGGER.info("Making request on {} to get all subscribed emails",uri);
        ResponseEntity<List> response = restTemplate.getForEntity(uri, List.class);
        LOGGER.info("Received response - {}", response);
        return response.getBody();
    }
}
