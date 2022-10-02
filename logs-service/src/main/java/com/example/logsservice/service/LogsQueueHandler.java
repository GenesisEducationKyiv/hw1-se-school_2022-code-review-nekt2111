package com.example.logsservice.service;

import com.example.logsservice.model.Log;
import com.example.logsservice.model.LogLevel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class LogsQueueHandler implements MessageQueueHandler {

    private final LogLevel logLevelToSout;

    private final ObjectMapper objectMapper;

    private static final Logger nativeLogger = LoggerFactory.getLogger(LogsQueueHandler.class);

    public LogsQueueHandler(LogLevel logLevel) {
        logLevelToSout = logLevel;
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    public void handleMessage(Message message) {
        Log log = new Log();
        try {
            log = objectMapper.readValue(message.getBody(), Log.class);
        }
        catch (Exception e) {
            nativeLogger.error("Exception occurred while mapping JSON to Log object with message - {}", e.getMessage());
        }

        soutLogWithSpecificLogType(log, logLevelToSout);
    }

    private static void soutLogWithSpecificLogType(Log log, LogLevel logLevel) {
        if (log.getLogLevel() == logLevel) {
            System.out.println(log);
        } else if (logLevel == LogLevel.ALL) {
            System.out.println(log);
        }
    }

}
