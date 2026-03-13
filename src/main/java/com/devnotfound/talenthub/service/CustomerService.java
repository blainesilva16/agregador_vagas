package com.devnotfound.talenthub.service;

import com.devnotfound.talenthub.constants.SystemConstants;
import com.devnotfound.talenthub.dto.CustomerRequestDTO;
import com.devnotfound.talenthub.dto.CustomerResponseDTO;
import com.devnotfound.talenthub.dto.CustomerUpdateDTO;
import com.devnotfound.talenthub.entity.Customer;
import com.devnotfound.talenthub.entity.User;
import com.devnotfound.talenthub.exception.DuplicateEmailException;
import com.devnotfound.talenthub.exception.ResourceNotFoundException;
import com.devnotfound.talenthub.mapper.CustomerMapper;
import com.devnotfound.talenthub.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerResponseDTO findByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(SystemConstants.CUSTOMER_NOT_FOUND_EMAIL + email));
        return CustomerMapper.toResponseDTO(customer);
    }

    public CustomerResponseDTO findById(Integer id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SystemConstants.CUSTOMER_NOT_FOUND_ID + id));
        return CustomerMapper.toResponseDTO(customer);
    }

    public List<CustomerResponseDTO> findAll() {
        return customerRepository.findAll().stream()
                .map(CustomerMapper::toResponseDTO)
                .toList();
    }

    public List<CustomerResponseDTO> findByName(String name) {
        List<CustomerResponseDTO> customerList = customerRepository.findByNameContainingIgnoreCase(name).stream()
                .map(CustomerMapper::toResponseDTO)
                .toList();

        if (customerList.isEmpty()) {
            throw new ResourceNotFoundException(SystemConstants.CUSTOMER_NOT_FOUND_NAME + name);
        }

        return customerList;
    }

    public CustomerResponseDTO insert(CustomerRequestDTO dto) {
        if (customerRepository.existsByEmail(dto.email())) {
            throw new DuplicateEmailException(SystemConstants.EMAIL_ALREADY_EXISTS + dto.email());
        }

        Customer customer = CustomerMapper.toEntity(dto);
        customer.setPassword(passwordEncoder.encode(dto.password()));

        Customer saved = customerRepository.save(customer);
        return CustomerMapper.toResponseDTO(saved);
    }

    public CustomerResponseDTO update(Integer id, CustomerUpdateDTO dto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SystemConstants.CUSTOMER_NOT_FOUND_ID + id));

        if (dto.email() != null && !dto.email().equals(customer.getEmail())) {
            if (customerRepository.existsByEmail(dto.email())) {
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

        Customer updated = customerRepository.save(customer);
        return CustomerMapper.toResponseDTO(updated);
    }

    public void delete(Integer id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException(SystemConstants.CUSTOMER_NOT_FOUND_ID + id);
        }
        customerRepository.deleteById(id);
    }
    
    public void updatePassword(Integer id, String newPassword) {
    	Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SystemConstants.CUSTOMER_NOT_FOUND_ID + id));
        customer.setPassword(passwordEncoder.encode(newPassword));
        customerRepository.save(customer);
    }

    public String resetPassword(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(SystemConstants.CUSTOMER_NOT_FOUND_EMAIL + email));

        String newPassword = SystemConstants.DEFAULT_SITE_PASSWORD;
        customer.setPassword(passwordEncoder.encode(newPassword));
        customerRepository.save(customer);

        return newPassword;
    }
    
    public void uploadPhoto(Integer id, MultipartFile file) throws Exception {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SystemConstants.CUSTOMER_NOT_FOUND_ID + id));

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Arquivo da foto é obrigatório.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("O arquivo enviado deve ser uma imagem.");
        }

        customer.setPhoto(file.getBytes());
        customerRepository.save(customer);
    }
    
    public ResponseEntity<byte[]> getPhoto(Integer id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SystemConstants.CUSTOMER_NOT_FOUND_ID + id));

        if (customer.getPhoto() == null || customer.getPhoto().length == 0) {
            throw new ResourceNotFoundException(SystemConstants.CUSTOMER_NOT_FOUND_PHOTO + id);
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(customer.getPhoto());
    }
    
}