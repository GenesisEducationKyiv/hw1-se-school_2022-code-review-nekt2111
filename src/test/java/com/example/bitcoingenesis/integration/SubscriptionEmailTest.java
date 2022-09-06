package com.example.bitcoingenesis.integration;

import com.example.bitcoingenesis.utill.FileUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.example.bitcoingenesis.util.TestConstants.EMAIL;
import static com.example.bitcoingenesis.util.TestConstants.MOCK_FILE_DB_LOCATION;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "db.file.path=/usr/src/app/src/mock-db.txt" })
@AutoConfigureMockMvc
public class SubscriptionEmailTest {
    @LocalServerPort
    private int port;

    @Value("${server.base.path}")
    private String baseUrl;

    @Value("${server.servlet.context-path}")
    private String defaultContextPath;

    @Autowired
    private RestTemplate restTemplate;

    private final static String EMAIL_TO_UNSUBSCRIBE = "emailDelete@gmail.com";

    private final static String EMAIL_TO_SUBSCRIBE = "subscribe@gmail.com";

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public static void init() {
        FileUtil.clearFile(MOCK_FILE_DB_LOCATION);
        List<String> mockEmails = List.of(EMAIL_TO_UNSUBSCRIBE, EMAIL);
        FileUtil.writeInFirstLineStringFromList(mockEmails, MOCK_FILE_DB_LOCATION, " ");
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat(defaultContextPath);
    }

    @Test
    public void subscribeEmail() throws Exception {
        RequestBuilder requestToSave = MockMvcRequestBuilders.post("/subscribe")
                .param("email",EMAIL_TO_SUBSCRIBE);

        MvcResult saveResult = mockMvc.perform(requestToSave).andReturn();
        MvcResult saveOneMoreTimeResult = mockMvc.perform(requestToSave).andReturn();

        assertEquals(200, saveResult.getResponse().getStatus());
        assertEquals(409, saveOneMoreTimeResult.getResponse().getStatus());
    }

    @Test
    public void subscribeEmailNotValid() throws Exception {
        RequestBuilder requestToSave = MockMvcRequestBuilders.post("/subscribe")
                .param("email","not_valid");

        MvcResult saveResult = mockMvc.perform(requestToSave).andReturn();

        assertEquals(400, saveResult.getResponse().getStatus());
    }

    @Test
    public void unsubscribeEmail() throws Exception {
        RequestBuilder unsubscribeRequest = MockMvcRequestBuilders.delete("/subscription")
                .param("email",EMAIL_TO_UNSUBSCRIBE);

        MvcResult unsubscribeResult = mockMvc.perform(unsubscribeRequest).andReturn();

        assertEquals(200, unsubscribeResult.getResponse().getStatus());
    }

    @Test
    public void findAllEmails() {
        List<String> emails = restTemplate.getForObject(baseUrl + "/subscription", List.class);

        assertNotNull(emails);
        assertNotEquals(0, emails.size());
    }
}
