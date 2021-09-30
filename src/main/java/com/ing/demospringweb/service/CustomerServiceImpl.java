package com.ing.demospringweb.service;

import com.ing.demospringweb.model.Customer;
import com.ing.demospringweb.model.dto.CustomerDto;
import com.ing.demospringweb.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository repository;

    @Override
    public Customer create(CustomerDto customerDto) {
        return repository.save(customerDto.toCustomer());
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Customer update(CustomerDto customerDto, Long id) {
        return repository.findById(id)
                .map(customer -> {
                    customer.setId(id);
                    customer.setName(customer.getName());
                    customer.setEmail(customer.getEmail());
                    return repository.save(customer);
                })
                .orElseGet(() -> {
                    customerDto.setId(id);
                    return repository.save(customerDto.toCustomer());
                });
    }

}
