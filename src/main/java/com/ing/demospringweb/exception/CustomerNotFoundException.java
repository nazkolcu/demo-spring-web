package com.ing.demospringweb.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(Long id) {
        super("Invalid customer Id:" + id);
    }
}
