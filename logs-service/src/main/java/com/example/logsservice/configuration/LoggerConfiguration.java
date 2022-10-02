package com.example.logsservice.configuration;

import com.example.logsservice.model.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfiguration {

    private final String logsLevelSetByUser;;

    public LoggerConfiguration(@Value("${show.logs.with.level}") String logsLevel) {
        this.logsLevelSetByUser = logsLevel;
    }

    @Bean
    public LogLevel logLevelSetByUser() {

        try {
           return LogLevel.valueOf(logsLevelSetByUser);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Illegal argument of logsLevel!");
        }
    }

}
