package com.example.customerapi.saga;

import com.example.customerapi.command.CreateCustomerCommand;
import com.example.customerapi.model.Customer;
import com.example.customerapi.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerSaga {

    private CustomerService customerService;

    private AmqpTemplate amqpTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerSaga.class);

    @Autowired
    private ObjectMapper objectMapper;

    public CustomerSaga(CustomerService customerService,
                        AmqpTemplate amqpTemplate) {
        this.customerService = customerService;
        this.amqpTemplate = amqpTemplate;
    }

    @RabbitListener(queues = "customer")
    private void onCreateCustomer(String in) throws Exception {
        CreateCustomerCommand cmd = objectMapper.readValue(in, CreateCustomerCommand.class);
        LOGGER.info("Received command to create customer - {}", cmd);
        Customer customer = new Customer();
        customer.setEmail(cmd.getEmail());
        try {
            customerService.createCustomer(customer);
            amqpTemplate.convertAndSend("success-create-customer", objectMapper.writeValueAsString(customer).getBytes());
        } catch (Exception e) {
            System.out.println(e);
            amqpTemplate.convertAndSend("failed-create-customer", objectMapper.writeValueAsString(customer).getBytes());
        }
    }
}
