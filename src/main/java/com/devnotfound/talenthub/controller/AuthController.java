package com.devnotfound.talenthub.controller;

import com.devnotfound.talenthub.dto.LoginRequestDTO;
import com.devnotfound.talenthub.dto.TokenResponseDTO;
import com.devnotfound.talenthub.service.ClienteAuthService;
import com.devnotfound.talenthub.service.TokenService;
import com.devnotfound.talenthub.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Rota responsável por gerar o Token!")
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;
    private final UserService userService;
    private final ClienteAuthService clienteAuthService;

    @PostMapping("/gerarToken")
    @Operation(summary = "Gerar Token", description = "Gera token de acesso à API para USER ou CLIENTE")
    public ResponseEntity<?> gerarToken(@RequestBody @Valid LoginRequestDTO dto) {

        String origem = dto.origem();

        if (!"USER".equalsIgnoreCase(origem) && !"CLIENTE".equalsIgnoreCase(origem)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Origem inválida!");
        }

        boolean authenticated =
                "USER".equalsIgnoreCase(origem)
                        ? userService.authenticate(dto.email(), dto.password())
                        : authenticateCliente(dto.email(), dto.password());

        if (!authenticated) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas!");
        }

        String token = tokenService.gerarToken(dto.email(), origem.toUpperCase());
        return ResponseEntity.ok(new TokenResponseDTO(token));
    }

    private boolean authenticateCliente(String email, String password) {
        try {
            clienteAuthService.authenticate(email, password); 
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}