package com.ing.demospringweb.model.dto;

import com.ing.demospringweb.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private long id;
    private String name;
    private String email;


    public Customer toCustomer() {
        return Customer.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .build();
    }
}
