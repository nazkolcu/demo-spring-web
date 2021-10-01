package com.ing.demospringweb.controller;


import com.ing.demospringweb.model.Customer;
import com.ing.demospringweb.model.MyResponse;
import com.ing.demospringweb.model.dto.CustomerDto;

import com.ing.demospringweb.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Validated
@RestController
@RequestMapping("v1/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping("/{id}")
    public CustomerDto getCustomerById(@PathVariable @Min(value = 1,message = "id must be greater than or equal to 1") Long id) {
        return service.findById(id).toCustomerDto();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable @Min(value = 1, message = "id must be greater than or equal to 1") Long id) {
        service.deleteById(id);
        return ResponseEntity.ok("The customer has been successfully deleted!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> replaceCustomer(@RequestBody @Valid CustomerDto customerDto, @PathVariable @Min(value = 1,message = "id must be greater than or equal to 1") Long id) {

        service.update(customerDto, id);
        return ResponseEntity.ok("The customer has been successfully updated!");
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createCustomerSendEmail(@RequestBody @Valid CustomerDto request) {

        Customer customer = service.create(request);

        WebClient client = WebClient.create("http://localhost:8092");
        MyResponse sendEmail = client.post()
                .uri("/api/sendemail")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(customer.toCustomerDto()), CustomerDto.class)
                .retrieve()
                .bodyToMono(MyResponse.class).block();

        return ResponseEntity.ok("The customer has been successfully created and " + sendEmail.getResult());

    }
}
