package com.devnotfound.talenthub.repository;

import com.devnotfound.talenthub.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    
    Customer findByEmail(String email);
    
    List<Customer> findByNameContainingIgnoreCase(String name);
}
