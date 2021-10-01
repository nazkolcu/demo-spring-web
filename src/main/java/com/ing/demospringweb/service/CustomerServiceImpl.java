package com.ing.demospringweb.service;

import com.ing.demospringweb.exception.CustomerNotFoundException;
import com.ing.demospringweb.model.Customer;
import com.ing.demospringweb.model.dto.CustomerDto;
import com.ing.demospringweb.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository repository;

    @Override
    public Customer create(CustomerDto customerDto) {
        return repository.save(customerDto.toCustomer());
    }

    @Override
    public Customer findById(Long id) {
        return repository.findById(id).orElseThrow(()->new CustomerNotFoundException(id));
    }

    @Override
    public void deleteById(Long id) {
        //repository.deleteById(id);
    Customer customer=findById(id);
    repository.deleteById(customer.getId());
    }

    @Override
    public Customer update(CustomerDto customerDto, Long id) {
        Customer customer=findById(id);
        customer.setEmail(customerDto.getEmail());
        customer.setName(customerDto.getName());
     return create(customer.toCustomerDto());

    }

}
