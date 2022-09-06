package com.example.bitcoingenesis.integration;

import com.example.bitcoingenesis.model.Currency;
import com.example.bitcoingenesis.utill.FileUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

import static com.example.bitcoingenesis.util.TestConstants.EMAIL;
import static com.example.bitcoingenesis.util.TestConstants.MOCK_FILE_DB_LOCATION;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "db.file.path=/usr/src/app/src/mock-db.txt" })
public class SendEmailsTest {

    @LocalServerPort
    private int port;

    @Value("${server.base.path}")
    private String baseUrl;

    @Value("${server.servlet.context-path}")
    private String defaultContextPath;

    @Autowired
    private static RestTemplate restTemplate;

    private static final String SEND_EMAILS_ENDPOINT = "/sendEmails";

    @BeforeAll
    public static void init() {
        FileUtil.clearFile(MOCK_FILE_DB_LOCATION);
        FileUtil.writeInFirstLineStringFromList(List.of(EMAIL), MOCK_FILE_DB_LOCATION, " ");
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat(defaultContextPath);
    }

    @Test
    public void sendEmails() {
        String uri = baseUrl + SEND_EMAILS_ENDPOINT;

        ResponseEntity<Void> response = restTemplate.postForEntity(uri, HttpEntity.EMPTY, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void sendEmailWithParameters() {
        String uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("currency", Currency.USD)
                .queryParam("crypto", "ethereum")
                .toUriString();

        ResponseEntity<Void> response = restTemplate.postForEntity(uri, HttpEntity.EMPTY, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
