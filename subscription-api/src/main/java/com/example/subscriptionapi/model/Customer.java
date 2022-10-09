package com.example.subscriptionapi.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Customer {
    private Integer id;
    private String email;
    private LocalDateTime createdAt;
}
