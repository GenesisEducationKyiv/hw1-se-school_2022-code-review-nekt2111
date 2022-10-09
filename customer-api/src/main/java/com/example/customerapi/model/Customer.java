package com.example.customerapi.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @JsonAlias(value = {"customerId", "id"})
    private Integer id;
    @Column(unique=true)
    private String email;
    private LocalDateTime createdAt;
}
