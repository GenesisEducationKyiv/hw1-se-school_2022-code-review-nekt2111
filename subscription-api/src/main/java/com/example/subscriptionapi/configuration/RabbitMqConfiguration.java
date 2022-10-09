package com.example.subscriptionapi.configuration;

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
    public Queue subscribeUserQueue() {
        return new Queue("subscribe-user");
    }

    @Bean
    public Queue FailedCreateCustomerQueue() {
        return new Queue("failed-create-customer");
    }

    @Bean
    public Queue SuccessCreateCustomerQueue() {
        return new Queue("success-create-customer");
    }
}
