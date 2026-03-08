package com.devnotfound.talenthub.mapper;

import com.devnotfound.talenthub.dto.CustomerRequestDTO;
import com.devnotfound.talenthub.dto.CustomerResponseDTO;
import com.devnotfound.talenthub.entity.Customer;

public final class CustomerMapper {

    private CustomerMapper() {}

    public static Customer toEntity(CustomerRequestDTO crDto) {
        Customer customers = new Customer();
        customers.setName(crDto.name());
        customers.setEmail(crDto.email());
        return customers;
    }

    public static CustomerResponseDTO toResponseDTO(Customer customers) {
        return new CustomerResponseDTO(
                customers.getId(),
                customers.getName(),
                customers.getEmail()
        );
    }
}