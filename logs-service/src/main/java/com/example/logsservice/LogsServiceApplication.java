package com.example.logsservice;

import com.example.logsservice.model.LogLevel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.net.ConnectException;

@SpringBootApplication
public class LogsServiceApplication {

	public static void main(String[] args) {
		try {
			ApplicationContext context = SpringApplication.run(LogsServiceApplication.class, args);
			LogLevel logLevel = context.getBean(LogLevel.class);
			System.out.println("Reading all logs with " + logLevel + " log level");
			SpringApplication.exit(context);
			System.out.println("Read all logs with " + logLevel + " log level");
		} catch (Exception exception) {
			System.out.println("Connection with rabbitMQ wasn't set");
			System.exit(1);
		}
	}

}
