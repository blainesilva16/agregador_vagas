package com.devnotfound.talenthub.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomCustomerDetailsService implements UserDetailsService {
    private final CustomerRepository customerRepository;

    public CustomCustomerDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer cliente = customerRepository.findByEmail(username);
        if (cliente == null) {
            throw new UsernameNotFoundException("Cliente não encontrado: " + username);
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(cliente.getEmail())
                .password(cliente.getPassword())
//                .roles("CUSTOMER")
                .build();
    }
}