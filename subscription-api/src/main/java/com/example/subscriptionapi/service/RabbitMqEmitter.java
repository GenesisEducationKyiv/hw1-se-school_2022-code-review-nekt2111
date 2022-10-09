package com.example.subscriptionapi.service;

import com.example.subscriptionapi.command.Command;

public interface RabbitMqEmitter {

    void emit(String queue, Command command);

}
