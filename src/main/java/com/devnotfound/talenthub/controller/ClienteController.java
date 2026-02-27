package com.devnotfound.talenthub.controller;

import com.devnotfound.talenthub.dto.ClienteRequestDTO;
import com.devnotfound.talenthub.dto.ClienteResponseDTO;
import com.devnotfound.talenthub.dto.LoginRequestDTO;
import com.devnotfound.talenthub.service.ClienteAuthService;
import com.devnotfound.talenthub.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cliente")
@RequiredArgsConstructor
public class ClienteController {

	private final ClienteAuthService clienteAuthService;
    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> insert(
            @RequestBody ClienteRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clienteService.insert(dto));
    }
    
    @PostMapping
    public ResponseEntity<String> login(
    		@RequestBody LoginRequestDTO dto) {
    	
    	clienteAuthService.authenticate(dto.email(), dto.password());
        return ResponseEntity.ok("Login realizado com sucesso!");
    }

    @PatchMapping("/{email}/resetpass")
    public ResponseEntity<String> resetPassword(
            @PathVariable String email) {

        String newPassword = clienteService.resetPassword(email);
        return ResponseEntity.ok(newPassword);
    }
    
}