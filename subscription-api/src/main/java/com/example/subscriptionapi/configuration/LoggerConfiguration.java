package com.example.subscriptionapi.configuration;


import com.example.subscriptionapi.service.logger.LoggerService;
import com.example.subscriptionapi.service.logger.LoggerServiceImpl;
import com.example.subscriptionapi.service.logger.LoggerServiceRabbitProxy;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfiguration {

    private final AmqpTemplate amqpTemplate;

    public LoggerConfiguration(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Bean
    public LoggerService loggerService() {
        LoggerService loggerService = new LoggerServiceImpl();
        return new LoggerServiceRabbitProxy(loggerService, amqpTemplate);
    }
}
