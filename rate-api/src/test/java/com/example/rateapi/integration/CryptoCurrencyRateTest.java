package com.example.rateapi.integration;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CryptoCurrencyRateTest {

    @LocalServerPort
    private int port;

    @Value("${server.base.path}")
    private String baseUrl;

    @Value("${server.servlet.context-path}")
    private String defaultContextPath;

    @DynamicPropertySource
    static void registerProperty(DynamicPropertyRegistry registry) {
        registry.add("crypto.rate.provider", () -> "coinbase");
    }

    @Autowired
    private MockMvc mockMvc;

    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat(defaultContextPath);
    }

    public void getRate() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/rate");

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();

        assertNotNull(mockHttpServletResponse.getContentAsString());
        assertTrue(Integer.parseInt(mockHttpServletResponse.getContentAsString()) > 0);
    }

    public void getRateForCrypto() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/rate/ethereum");

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();

        assertNotNull(mockHttpServletResponse.getContentAsString());
        assertTrue(Integer.parseInt(mockHttpServletResponse.getContentAsString()) > 0);
    }

}