package com.example.subscriptionapi.service;

import com.example.subscriptionapi.command.Command;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqEmitterImpl implements RabbitMqEmitter {

    private AmqpTemplate amqpTemplate;

    private static Logger LOGGER = LoggerFactory.getLogger(RabbitMqEmitter.class);

    public RabbitMqEmitterImpl(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public void emit(String queue, Command command) {

        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";

        try {
             json = objectMapper.writeValueAsString(command);
             LOGGER.info("Emitting json - {} to queue {}", json, queue);
        } catch (Exception e) {
            System.out.println("Exception with mapping");
        }

        amqpTemplate.convertAndSend(queue, json);
    }
}
