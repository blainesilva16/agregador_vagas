package com.devnotfound.talenthub.controller;

import com.devnotfound.talenthub.dto.CustomerRequestDTO;
import com.devnotfound.talenthub.dto.CustomerResponseDTO;
import com.devnotfound.talenthub.dto.CustomerUpdateDTO;
import com.devnotfound.talenthub.dto.ResetPasswordRequestDTO;
import com.devnotfound.talenthub.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customersService;

    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerResponseDTO> findByEmail(@PathVariable String email) {
        return ResponseEntity.ok(customersService.findByEmail(email));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(customersService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> findAll() {
        return ResponseEntity.ok(customersService.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomerResponseDTO>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(customersService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> insert(@RequestBody @Valid CustomerRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customersService.insert(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable Long id, @RequestBody @Valid CustomerUpdateDTO dto) {
        return ResponseEntity.ok(customersService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customersService.delete(id);
        return ResponseEntity.noContent().build();
    }
   
    @PatchMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid ResetPasswordRequestDTO dto) {
        String newPassword = customersService.resetPassword(dto.email());
        return ResponseEntity.ok(newPassword);
    }
}