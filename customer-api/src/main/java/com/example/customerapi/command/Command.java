package com.example.customerapi.command;

import lombok.Data;

@Data
public abstract class Command {
    private Integer customerId;
}
