package com.ing.demospringweb.service;

import com.ing.demospringweb.model.Customer;
import com.ing.demospringweb.model.dto.CustomerDto;

public interface CustomerService {
    Customer create(CustomerDto customerDto);

    Customer findById(Long id);

    void deleteById(Long id);

    Customer update(CustomerDto customerDto, Long id);
}
