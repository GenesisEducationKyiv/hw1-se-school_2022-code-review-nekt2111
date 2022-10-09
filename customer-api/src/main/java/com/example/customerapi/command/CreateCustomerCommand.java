package com.example.customerapi.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CreateCustomerCommand extends Command {
    private String email;

    @JsonCreator
    public CreateCustomerCommand(@JsonProperty(value = "email") String email) {
        this.email = email;
    }
}
