package com.devnotfound.talenthub.controller;

import com.devnotfound.talenthub.dto.CustomerRequestDTO;
import com.devnotfound.talenthub.dto.CustomerResponseDTO;
import com.devnotfound.talenthub.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    
    private final CustomerService customerService;

    @GetMapping("/home")
    public String homeCustomer() {
        return "customer/home";
    }

    @GetMapping("/login-customer")
    public String loginCustomer() {
        return "customer/login";
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerResponseDTO> findByEmail(@PathVariable String email) {
        return ResponseEntity.ok(customerService.findByEmail(email));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(customerService.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> findAll() {
        return ResponseEntity.ok(customerService.findAll());
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<CustomerResponseDTO>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(customerService.findByName(name));
    }
    
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> insert(@RequestBody CustomerRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.insert(dto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable Integer id, @RequestBody CustomerRequestDTO dto) {
        return ResponseEntity.ok(customerService.update(id, dto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable Integer id, @RequestBody String newPassword) {
        customerService.updatePassword(id, newPassword);
        return ResponseEntity.noContent().build();
    }
}