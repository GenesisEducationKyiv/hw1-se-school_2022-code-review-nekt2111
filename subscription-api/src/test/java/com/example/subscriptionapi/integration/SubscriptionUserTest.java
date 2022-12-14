package com.example.subscriptionapi.integration;

import com.example.subscriptionapi.utill.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.example.subscriptionapi.TestConstants.EMAIL;
import static com.example.subscriptionapi.TestConstants.MOCK_FILE_DB_LOCATION;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
public class SubscriptionUserTest {
    @LocalServerPort
    private int port;

    @Value("${server.base.path}")
    private String baseUrl;

    @Value("${server.servlet.context-path}")
    private String defaultContextPath;

    private RestTemplate restTemplate;

    private final static String EMAIL_TO_UNSUBSCRIBE = "emailDelete@gmail.com";

    private final static String EMAIL_TO_SUBSCRIBE = "subscribe@gmail.com";

    @Autowired
    private MockMvc mockMvc;

    @DynamicPropertySource
    static void registerProperty(DynamicPropertyRegistry registry) {
        registry.add("db.file.path", () -> MOCK_FILE_DB_LOCATION);
    }

    public static void init() {
        FileUtil.clearFile(MOCK_FILE_DB_LOCATION);
        List<String> mockEmails = List.of(EMAIL_TO_UNSUBSCRIBE, EMAIL);
        FileUtil.writeInFirstLineStringFromList(mockEmails, MOCK_FILE_DB_LOCATION, " ");
    }

    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat(defaultContextPath);
        restTemplate = new RestTemplate();
    }


    public void subscribeEmail() throws Exception {
        RequestBuilder requestToSave = MockMvcRequestBuilders.post("/subscription")
                .param("email",EMAIL_TO_SUBSCRIBE);

        MvcResult saveResult = mockMvc.perform(requestToSave).andReturn();
        MvcResult saveOneMoreTimeResult = mockMvc.perform(requestToSave).andReturn();

        assertEquals(200, saveResult.getResponse().getStatus());
        assertEquals(409, saveOneMoreTimeResult.getResponse().getStatus());
    }


    public void subscribeEmailNotValid() throws Exception {
        RequestBuilder requestToSave = MockMvcRequestBuilders.post("/subscription")
                .param("email","not_valid");

        MvcResult saveResult = mockMvc.perform(requestToSave).andReturn();

        assertEquals(400, saveResult.getResponse().getStatus());
    }


    public void unsubscribeEmail() throws Exception {
        RequestBuilder unsubscribeRequest = MockMvcRequestBuilders.delete("/subscription")
                .param("email",EMAIL_TO_UNSUBSCRIBE);

        MvcResult unsubscribeResult = mockMvc.perform(unsubscribeRequest).andReturn();

        assertEquals(200, unsubscribeResult.getResponse().getStatus());
    }


    public void findAllEmails() {
        List<String> emails = restTemplate.getForObject(baseUrl + "/subscription/emails", List.class);

        assertNotNull(emails);
        assertNotEquals(0, emails.size());
    }
}