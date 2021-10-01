package com.ing.demospringweb.controller;


import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing.demospringweb.model.Customer;
import com.ing.demospringweb.model.dto.CustomerDto;
import com.ing.demospringweb.service.CustomerService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("v2/customer")
public class CustomerController2 {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CustomerService service;

    @GetMapping("/{id}")
    public CustomerDto getCustomerById(@PathVariable @Min(value = 1, message = "id must be greater than or equal to 1") Long id) {
        return service.findById(id).toCustomerDto();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable @Min(value = 1, message = "id must be greater than or equal to 1") Long id) {
        service.deleteById(id);
        return ResponseEntity.ok("The customer has been successfully deleted!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> replaceCustomer(@RequestBody @Valid CustomerDto customerDto, @PathVariable @Min(value = 1, message = "id must be greater than or equal to 1") Long id) {

        service.update(customerDto, id);
        return ResponseEntity.ok("The customer has been successfully updated!");
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createCustomerSendEmail(@RequestBody @Valid CustomerDto request) throws JacksonException {

        Customer customer = service.create(request);
        ObjectMapper objectMapper = new ObjectMapper();
        String rmqmessage = objectMapper.writeValueAsString(customer.toCustomerDto());

        rabbitTemplate.convertAndSend("EVENTMESSAGE.EXCHANGE", "DEMO.QUEUE", rmqmessage);


        return ResponseEntity.ok("The customer has been successfully created!");
    }
}
