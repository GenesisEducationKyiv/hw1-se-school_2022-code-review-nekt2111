package com.example.customerapi.service;

import com.example.customerapi.model.Customer;
import com.example.customerapi.repo.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void createCustomer(Customer customer) {
        customer.setCreatedAt(LocalDateTime.now());
        customerRepository.save(customer);
    }

}
