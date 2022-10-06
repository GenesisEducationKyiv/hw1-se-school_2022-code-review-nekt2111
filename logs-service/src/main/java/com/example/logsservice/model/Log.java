package com.example.logsservice.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class Log {
    private String message;
    private LogLevel logLevel;
    @DateTimeFormat
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime localDateTime;
    private String apiFromLogging;

    @Override
    public String toString() {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")) + " " + apiFromLogging + " " + logLevel + " " + message;
    }
}
