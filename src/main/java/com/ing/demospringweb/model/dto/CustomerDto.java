package com.ing.demospringweb.model.dto;

import com.ing.demospringweb.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private long id;
    @NotEmpty(message = "Name should not be null or empty")
    private String name;

    @Email(message = "The email address is invalid.", flags = { Pattern.Flag.CASE_INSENSITIVE })
    @NotEmpty(message = "Email should not be null or empty")
    private String email;


    public Customer toCustomer() {
        return Customer.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .build();
    }
}
