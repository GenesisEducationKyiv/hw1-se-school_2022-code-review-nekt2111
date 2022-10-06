package com.example.notificationapi.client;

import com.example.notificationapi.service.logger.LoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.lang.String.format;

@Component
public class UserClientImpl implements UserClient {

    private final String userApiUrl;
    private final RestTemplate restTemplate;

    private LoggerService loggerService;


    public UserClientImpl(@Value("${bitcoin.genesis.user.api.url}") String userApiUrl,
                          RestTemplate restTemplate,
                          LoggerService loggerService) {
        this.userApiUrl = userApiUrl;
        this.restTemplate = restTemplate;
        this.loggerService = loggerService;
        this.loggerService.setOutputClassName(this.getClass().getName());
    }

    @Override
    public List<String> getAllUsersSubscribedEmails() {
        String uri = userApiUrl + "/subscription/emails";
        loggerService.logInfo(format("Making request on %s to get all subscribed emails",uri));
        ResponseEntity<List> response = restTemplate.getForEntity(uri, List.class);
        loggerService.logDebug(format("Received response - %s", response));
        return response.getBody();
    }
}
