package com.example.subscriptionapi.command;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCustomerCommand extends Command {
    private String email;
}
