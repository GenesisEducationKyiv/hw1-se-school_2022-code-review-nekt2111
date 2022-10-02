package com.example.logsservice.configuration;

import com.example.logsservice.service.MessageQueueHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {

    private static final Logger nativeLogger = LoggerFactory.getLogger(RabbitMqConfiguration.class);

    private final MessageQueueHandler messageQueueHandler;

    public RabbitMqConfiguration(MessageQueueHandler messageQueueHandler) {
        this.messageQueueHandler = messageQueueHandler;
    }
    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("rabbit");
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Queue myQueue1() {
        return new Queue("logs-queue");
    }

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueueNames("logs-queue");
        container.setMessageListener((messageQueueHandler::handleMessage));
        return container;
    }
}
