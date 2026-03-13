package com.devnotfound.talenthub.service;

import com.devnotfound.talenthub.constants.SystemConstants;
import com.devnotfound.talenthub.dto.CustomerRequestDTO;
import com.devnotfound.talenthub.dto.CustomerResponseDTO;
import com.devnotfound.talenthub.dto.CustomerUpdateDTO;
import com.devnotfound.talenthub.entity.Customer;
import com.devnotfound.talenthub.exception.DuplicateEmailException;
import com.devnotfound.talenthub.exception.ResourceNotFoundException;
import com.devnotfound.talenthub.mapper.CustomerMapper;
import com.devnotfound.talenthub.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customersRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerResponseDTO findByEmail(String email) {
        Customer customer = customersRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(SystemConstants.CUSTOMER_NOT_FOUND_EMAIL + email));
        return CustomerMapper.toResponseDTO(customer);
    }

    public CustomerResponseDTO findById(Long id) {
        Customer customer = customersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SystemConstants.CUSTOMER_NOT_FOUND_ID + id));
        return CustomerMapper.toResponseDTO(customer);
    }

    public List<CustomerResponseDTO> findAll() {
        return customersRepository.findAll().stream()
                .map(CustomerMapper::toResponseDTO)
                .toList();
    }

    public List<CustomerResponseDTO> findByName(String name) {
        List<CustomerResponseDTO> customersList = customersRepository.findByNameContainingIgnoreCase(name).stream()
                .map(CustomerMapper::toResponseDTO)
                .toList();

        if (customersList.isEmpty()) {
            throw new ResourceNotFoundException(SystemConstants.CUSTOMER_NOT_FOUND_NAME + name);
        }

        return customersList;
    }

    public CustomerResponseDTO insert(CustomerRequestDTO dto) {
        if (customersRepository.existsByEmail(dto.email())) {
            throw new DuplicateEmailException(SystemConstants.EMAIL_ALREADY_EXISTS + dto.email());
        }

        Customer customer = CustomerMapper.toEntity(dto);
        customer.setPassword(passwordEncoder.encode(dto.password()));

        Customer saved = customersRepository.save(customer);
        return CustomerMapper.toResponseDTO(saved);
    }

    public CustomerResponseDTO update(Long id, CustomerUpdateDTO dto) {
        Customer customer = customersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SystemConstants.CUSTOMER_NOT_FOUND_ID + id));

        if (dto.email() != null && !dto.email().equals(customer.getEmail())) {
            if (customersRepository.existsByEmail(dto.email())) {
                throw new DuplicateEmailException(SystemConstants.EMAIL_ALREADY_EXISTS + dto.email());
            }
            customer.setEmail(dto.email());
        }

        if (dto.name() != null) {
            customer.setName(dto.name());
        }

        if (dto.birthdate() != null) {
            customer.setBirthdate(dto.birthdate());
        }

        if (dto.photo() != null) {
            customer.setPhoto(dto.photo());
        }

        Customer updated = customersRepository.save(customer);
        return CustomerMapper.toResponseDTO(updated);
    }

    public void delete(Long id) {
        if (!customersRepository.existsById(id)) {
            throw new ResourceNotFoundException(SystemConstants.CUSTOMER_NOT_FOUND_ID + id);
        }
        customersRepository.deleteById(id);
    }

    public String resetPassword(String email) {
        Customer customer = customersRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(SystemConstants.CUSTOMER_NOT_FOUND_EMAIL + email));

        String newPassword = SystemConstants.DEFAULT_SITE_PASSWORD;
        customer.setPassword(passwordEncoder.encode(newPassword));
        customersRepository.save(customer);

        return newPassword;
    }
}