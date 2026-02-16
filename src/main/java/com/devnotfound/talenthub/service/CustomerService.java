package com.devnotfound.talenthub.service;

import com.devnotfound.talenthub.dto.CustomerRequestDTO;
import com.devnotfound.talenthub.dto.CustomerResponseDTO;
import com.devnotfound.talenthub.entity.Customer;
import com.devnotfound.talenthub.mapper.CustomerMapper;
import com.devnotfound.talenthub.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerResponseDTO findByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email);
        return CustomerMapper.toResponseDTO(customer);
    }

    public CustomerResponseDTO findById(Integer id) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        return CustomerMapper.toResponseDTO(customer);
    }

    public List<CustomerResponseDTO> findAll() {
        return customerRepository.findAll().stream()
                .map(CustomerMapper::toResponseDTO)
                .toList();
    }

    public List<CustomerResponseDTO> findByName(String name) {
        return customerRepository.findByNameContainingIgnoreCase(name).stream()
                .map(CustomerMapper::toResponseDTO)
                .toList();
    }

    public CustomerResponseDTO insert(CustomerRequestDTO dto) {
        Customer customer = CustomerMapper.toEntity(dto);
        Customer saved = customerRepository.save(customer);
        return CustomerMapper.toResponseDTO(saved);
    }

    public CustomerResponseDTO update(Integer id, CustomerRequestDTO dto) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        customer.setName(dto.name());
        customer.setEmail(dto.email());
        customer.setPassword(dto.password());
        Customer updated = customerRepository.save(customer);
        return CustomerMapper.toResponseDTO(updated);
    }

    public void delete(Integer id) {
        customerRepository.deleteById(id);
    }

    public void updatePassword(Integer id, String newPassword) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        customer.setPassword(newPassword);
        customerRepository.save(customer);
    }
}
