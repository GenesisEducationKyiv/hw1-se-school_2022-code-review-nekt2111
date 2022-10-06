package com.example.userapi.model.logs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log implements Serializable {
    private String message;
    private LogLevel logLevel;
    @DateTimeFormat
    private LocalDateTime localDateTime;
    private String apiFromLogging;
}
