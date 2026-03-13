package com.devnotfound.talenthub.controller;

import com.devnotfound.talenthub.dto.CustomerRequestDTO;
import com.devnotfound.talenthub.dto.CustomerResponseDTO;
import com.devnotfound.talenthub.dto.CustomerUpdateDTO;
import com.devnotfound.talenthub.dto.ResetPasswordRequestDTO;
import com.devnotfound.talenthub.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

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
    
    @GetMapping("/{id}/photo")
    public ResponseEntity<byte[]> getPhoto(@PathVariable Integer id) {
        return customerService.getPhoto(id);
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> insert(@RequestBody @Valid CustomerRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.insert(dto));
    }
    
    @PostMapping(value = "/{id}/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadPhoto(@PathVariable Integer id,
                                              @RequestParam("file") MultipartFile file) throws Exception {
        customerService.uploadPhoto(id, file);
        return ResponseEntity.ok("Foto atualizada com sucesso.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable Integer id, @RequestBody @Valid CustomerUpdateDTO dto) {
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
    
    @PatchMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid ResetPasswordRequestDTO dto) {
        String newPassword = customerService.resetPassword(dto.email());
        return ResponseEntity.ok(newPassword);
    }
}