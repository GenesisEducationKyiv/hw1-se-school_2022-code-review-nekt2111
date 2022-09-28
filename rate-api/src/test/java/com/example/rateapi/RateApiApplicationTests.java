package com.example.rateapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest()
class RateApiApplicationTests {

    @DynamicPropertySource
    static void registerProperty(DynamicPropertyRegistry registry) {
        registry.add("crypto.rate.provider", () -> "coinbase");
    }

    @Test
    void contextLoads() {
    }

}
