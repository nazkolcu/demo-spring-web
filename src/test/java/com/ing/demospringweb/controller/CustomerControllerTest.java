package com.ing.demospringweb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ing.demospringweb.AbstractTest;
import com.ing.demospringweb.model.Customer;
import com.ing.demospringweb.model.dto.CustomerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

class CustomerControllerTest extends AbstractTest {


    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    void getCustomerById() throws Exception {
        String uri = "/v1/customer/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        CustomerDto customer = new CustomerDto();
        customer.setId(1);
        customer.setEmail("nazklc@gmail.com");
        customer.setName("Naz Kolcu");
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        CustomerDto customer2 = super.mapFromJson(content, CustomerDto.class);
        assertEquals(customer, customer2);
    }

    @Test
    void getCustomerByIdWithNotFoundCustomer() throws Exception {
        String uri = "/v1/customer/11";
        CustomerDto customer = new CustomerDto();
        customer.setId(11);
        customer.setEmail("nazklc@gmail.com");
        customer.setName("Naz Kolcu");
        String inputJson = super.mapToJson(customer);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "{\"status\":404,\"error\":\"Invalid customer Id:11\"}");
    }


    @Test
    void updateCustomer() throws Exception {
        String uri = "/v1/customer/1";
        CustomerDto customer = new CustomerDto();
        customer.setId(1);
        customer.setEmail("nazklc@gmail.com");
        customer.setName("Naz Kolcu");
        String inputJson = super.mapToJson(customer);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "The customer has been successfully updated!");
    }

    @Test
    void updateCustomerWithInvalidId() throws Exception {
        String uri = "/v1/customer/0";
        CustomerDto customer = new CustomerDto();
        customer.setId(0);
        customer.setEmail("nazklc@gmail.com");
        customer.setName("Naz Kolcu");
        String inputJson = super.mapToJson(customer);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "{\"status\":200,\"error\":\"replaceCustomer.id: id must be greater than or equal to 1\"}");
    }


    @Test
    void updateCustomerWithNotFoundCustomer() throws Exception {
        String uri = "/v1/customer/11";
        CustomerDto customer = new CustomerDto();
        customer.setId(11);
        customer.setEmail("nazklc@gmail.com");
        customer.setName("Naz Kolcu");
        String inputJson = super.mapToJson(customer);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "{\"status\":404,\"error\":\"Invalid customer Id:11\"}");
    }


    @Test
    void deleteCustomer() throws Exception {
        String uri = "/v1/customer/2";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "The customer has been successfully deleted!");
    }

    @Test
    void deleteCustomerWithInvalidId() throws Exception {
        String uri = "/v1/customer/0";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "{\"status\":200,\"error\":\"deleteCustomer.id: id must be greater than or equal to 1\"}");
    }

    @Test
    void deleteCustomerWithNotFoundCustomer() throws Exception {
        String uri = "/v1/customer/22";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "{\"status\":404,\"error\":\"Invalid customer Id:22\"}");
    }

}