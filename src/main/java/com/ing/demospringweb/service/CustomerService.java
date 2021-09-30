package com.ing.demospringweb.service;

import com.ing.demospringweb.model.Customer;
import com.ing.demospringweb.model.dto.CustomerDto;

import java.util.Optional;

public interface CustomerService {
    Customer create(CustomerDto customerDto);

    Optional<Customer> findById(Long id);

    void deleteById(Long id);

    Customer update(CustomerDto customerDto, Long id);
}
