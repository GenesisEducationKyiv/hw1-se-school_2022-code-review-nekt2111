package com.example.logsservice.service;

import org.springframework.amqp.core.Message;

public interface MessageQueueHandler {
    void handleMessage(Message message);
}
