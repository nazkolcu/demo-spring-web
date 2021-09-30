package com.ing.demospringweb.controller;


import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing.demospringweb.exception.CustomerNotFoundException;
import com.ing.demospringweb.model.Customer;
import com.ing.demospringweb.model.MyResponse;
import com.ing.demospringweb.model.dto.CustomerDto;
import com.ing.demospringweb.service.CustomerService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("v2/customer")
public class CustomerController2 {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CustomerService service;

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return service.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        service.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity replaceEmployee(@RequestBody CustomerDto customerDto, @PathVariable Long id) {

        service.update(customerDto, id);
        return ResponseEntity.ok("Musteri basariyla guncellendi!");
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createCustomerSendEmail(@RequestBody CustomerDto request) throws JacksonException {

        Customer customer = service.create(request);
        ObjectMapper objectMapper = new ObjectMapper();
        String rmqmessage = objectMapper.writeValueAsString(customer.toCustomerDto());

        rabbitTemplate.convertAndSend("EVENTMESSAGE.EXCHANGE", "DEMO.QUEUE", rmqmessage);


        return ResponseEntity.ok("Musteri basariyla yaratildi!");
    }
}
