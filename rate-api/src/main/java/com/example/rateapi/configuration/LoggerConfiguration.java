package com.example.rateapi.configuration;

import com.example.rateapi.service.logger.LoggerService;
import com.example.rateapi.service.logger.LoggerServiceImpl;
import com.example.rateapi.service.logger.LoggerServiceRabbitProxy;
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
