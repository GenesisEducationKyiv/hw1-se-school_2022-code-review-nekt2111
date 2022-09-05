package com.example.bitcoingenesis.integration;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CryptoCurrencyRateTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    @Autowired
    private static RestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api");
    }

    @Test
    public void getRate() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/rate");

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();

        assertNotNull(mockHttpServletResponse.getContentAsString());
        assertTrue(Integer.parseInt(mockHttpServletResponse.getContentAsString()) > 0);
    }

    @Test
    public void getRateForCrypto() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/rate/ethereum");

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();

        assertNotNull(mockHttpServletResponse.getContentAsString());
        assertTrue(Integer.parseInt(mockHttpServletResponse.getContentAsString()) > 0);
    }

}
