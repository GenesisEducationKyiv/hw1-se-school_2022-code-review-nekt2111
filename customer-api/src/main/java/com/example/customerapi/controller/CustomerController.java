package com.example.customerapi.controller;

import com.example.customerapi.model.Customer;
import com.example.customerapi.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping
    public void createUser() {
        Customer customer = new Customer();

        customerRepository.save(customer);
    }


}
