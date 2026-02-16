package com.devnotfound.talenthub.mapper;

import com.devnotfound.talenthub.dto.CustomerRequestDTO;
import com.devnotfound.talenthub.dto.CustomerResponseDTO;
import com.devnotfound.talenthub.entity.Customer;

public class CustomerMapper {

    public static Customer toEntity(CustomerRequestDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.name());
        customer.setEmail(dto.email());
        customer.setPassword(dto.password());
        return customer;
    }

    public static CustomerResponseDTO toResponseDTO(Customer customer) {
        return new CustomerResponseDTO(
            customer.getId(),
            customer.getName(),
            customer.getEmail(),
            customer.getCreationDate()
        );
    }
}
