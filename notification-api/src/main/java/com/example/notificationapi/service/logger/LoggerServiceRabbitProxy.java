package com.example.notificationapi.service.logger;

import com.example.notificationapi.model.logs.Log;
import com.example.notificationapi.model.logs.LogLevel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;

import java.time.LocalDateTime;

public class LoggerServiceRabbitProxy implements LoggerService {

    private final LoggerService loggerService;

    private final static Logger nativeLogger = LoggerFactory.getLogger(LoggerServiceRabbitProxy.class);

    private final AmqpTemplate amqpTemplate;

    private final ObjectMapper objectMapper;

    public LoggerServiceRabbitProxy(LoggerService loggerService,
                                    AmqpTemplate amqpTemplate) {
        this.loggerService = loggerService;
        this.amqpTemplate = amqpTemplate;
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void logInfo(String message) {
        loggerService.logInfo(message);
        sendLogToRabbit(message, LogLevel.INFO);
    }

    @Override
    public void logError(String message) {
        loggerService.logError(message);
        sendLogToRabbit(message, LogLevel.ERROR);
    }

    @Override
    public void logDebug(String message) {
        loggerService.logDebug(message);
        sendLogToRabbit(message, LogLevel.DEBUG);
    }

    @Override
    public void setOutputClassName(String className) {
        loggerService.setOutputClassName(className);
    }

    @Override
    public String getOutputClassName() {
        return loggerService.getOutputClassName();
    }

    private void sendLogToRabbit(String message, LogLevel logLevel) {
        Log log = new Log(message, logLevel, LocalDateTime.now(), "Notification API");
        String jsonLogStr = "";
        try {
             jsonLogStr = objectMapper.writeValueAsString(log);
        } catch (Exception exception) {
            nativeLogger.error("Error occurred while mapping Log object to JSON with message - {}", exception.getMessage());
        }
        nativeLogger.info("Sending log - {} to RabbitMQ", jsonLogStr);
        amqpTemplate.convertAndSend("logs-queue", jsonLogStr.getBytes());
    }
}
