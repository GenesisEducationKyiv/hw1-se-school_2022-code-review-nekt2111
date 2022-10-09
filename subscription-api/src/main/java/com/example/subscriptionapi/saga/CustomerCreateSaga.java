package com.example.subscriptionapi.saga;

import com.example.subscriptionapi.command.CreateCustomerCommand;
import com.example.subscriptionapi.command.CreateUserSubscriptionCommand;
import com.example.subscriptionapi.model.Customer;
import com.example.subscriptionapi.model.User;
import com.example.subscriptionapi.service.RabbitMqEmitter;
import com.example.subscriptionapi.service.SubscriptionUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerCreateSaga {

    private final SubscriptionUserService subscriptionUserService;

    private final RabbitMqEmitter rabbitMqEmitter;
    
    private final static Logger LOGGER = LoggerFactory.getLogger(CustomerCreateSaga.class);

    @Autowired
    private ObjectMapper objectMapper;

    public CustomerCreateSaga(SubscriptionUserService subscriptionUserService, RabbitMqEmitter rabbitMqEmitter, AmqpTemplate amqpTemplate) {
        this.subscriptionUserService = subscriptionUserService;
        this.rabbitMqEmitter = rabbitMqEmitter;
    }

    @RabbitListener(queues = "subscribe-user")
    private void onSubscribeUser(String in) throws Exception {
        System.out.println(in);
        LOGGER.info("Received user subscribe command - {}", in);
        CreateUserSubscriptionCommand cmd;
        try {
            cmd = objectMapper.readValue(in, CreateUserSubscriptionCommand.class);
            subscriptionUserService.subscribe(cmd.getUser());
            rabbitMqEmitter.emit("customer", new CreateCustomerCommand(cmd.getUser().getEmail()));
        }
        catch (Exception e) {
            LOGGER.error("Error occurred with exception - {}", e.getMessage());
        }
    }

    @RabbitListener(queues = "failed-create-customer")
    private void onFailedCreateCustomer(String in) throws Exception {
        System.out.println(in);
        LOGGER.info("Failed to create customer - {}", in);

        Customer customer = objectMapper.readValue(in, Customer.class);
        User user = new User(customer.getEmail());

        subscriptionUserService.unsubscribe(user);
    }

    @RabbitListener(queues = "success-create-customer")
    private void onSuccessCreateCustomer(String in) {
        LOGGER.info("Successfully created customer with response {}", in);
    }
}
