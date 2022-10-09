package com.example.subscriptionapi.command;

import com.example.subscriptionapi.model.User;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CreateUserSubscriptionCommand extends Command {
    private User user;

    @JsonCreator
    public CreateUserSubscriptionCommand(@JsonProperty(value = "user") User user) {
        this.user = user;
    }

}
