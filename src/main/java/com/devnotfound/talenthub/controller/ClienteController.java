package com.devnotfound.talenthub.controller;

import com.devnotfound.talenthub.dto.ClienteRequestDTO;
import com.devnotfound.talenthub.dto.ClienteResponseDTO;
import com.devnotfound.talenthub.dto.ClienteUpdateDTO;
import com.devnotfound.talenthub.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping("/email/{email}")
    public ResponseEntity<ClienteResponseDTO> findByEmail(@PathVariable String email) {
        return ResponseEntity.ok(clienteService.findByEmail(email));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.findById(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<ClienteResponseDTO>> findAll() {
        return ResponseEntity.ok(clienteService.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ClienteResponseDTO>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(clienteService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> insert(@RequestBody @Valid ClienteRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.insert(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ClienteUpdateDTO dto) {
        return ResponseEntity.ok(clienteService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{email}/resetpass")
    public ResponseEntity<String> resetPassword(@PathVariable String email) {
        String newPassword = clienteService.resetPassword(email);
        return ResponseEntity.ok(newPassword);
    }
}