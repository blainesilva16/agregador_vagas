package com.devnotfound.talenthub.service;

import com.devnotfound.talenthub.dto.CustomerRequestDTO;
import com.devnotfound.talenthub.dto.CustomerResponseDTO;
import com.devnotfound.talenthub.entity.Customer;
import com.devnotfound.talenthub.exception.DuplicateEmailException;
import com.devnotfound.talenthub.exception.ResourceNotFoundException;
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
        if (customer == null) {
            throw new ResourceNotFoundException("Customer not found with email: " + email);
        }
        return CustomerMapper.toResponseDTO(customer);
    }

    public CustomerResponseDTO findById(Integer id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        return CustomerMapper.toResponseDTO(customer);
    }

    public List<CustomerResponseDTO> findAll() {
        return customerRepository.findAll().stream()
                .map(CustomerMapper::toResponseDTO)
                .toList();
    }

    public List<CustomerResponseDTO> findByName(String name) {
        List<CustomerResponseDTO> customers = customerRepository.findByNameContainingIgnoreCase(name).stream()
                .map(CustomerMapper::toResponseDTO)
                .toList();
        if (customers.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum cliente encontrado com o nome: " + name);
        }
        return customers;
    }

    public CustomerResponseDTO insert(CustomerRequestDTO dto) {
        Customer existingCustomer = customerRepository.findByEmail(dto.email());
        if (existingCustomer != null) {
            throw new DuplicateEmailException("Email já cadastrado: " + dto.email());
        }
        Customer customer = CustomerMapper.toEntity(dto);
        Customer saved = customerRepository.save(customer);
        return CustomerMapper.toResponseDTO(saved);
    }

    public CustomerResponseDTO update(Integer id, CustomerRequestDTO dto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        
        Customer existingCustomer = customerRepository.findByEmail(dto.email());
        if (existingCustomer != null && !existingCustomer.getId().equals(id)) {
            throw new DuplicateEmailException("Email já cadastrado: " + dto.email());
        }
        
        customer.setName(dto.name());
        customer.setEmail(dto.email());
        customer.setPassword(dto.password());
        Customer updated = customerRepository.save(customer);
        return CustomerMapper.toResponseDTO(updated);
    }

    public void delete(Integer id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }

    public void updatePassword(Integer id, String newPassword) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        customer.setPassword(newPassword);
        customerRepository.save(customer);
    }
}
