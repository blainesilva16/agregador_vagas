package com.devnotfound.talenthub.service;

import com.devnotfound.talenthub.entity.Customer;
import com.devnotfound.talenthub.exception.ResourceNotFoundException;
import com.devnotfound.talenthub.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticatedCustomerService {

    private final CustomerRepository customerRepository;

    public Customer getLoggedCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getName())) {
            throw new BadCredentialsException("Customer não autenticado.");
        }

        String email = authentication.getName();

        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer logado não encontrado: " + email));
    }
}