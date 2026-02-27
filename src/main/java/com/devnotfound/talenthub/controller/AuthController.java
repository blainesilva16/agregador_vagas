package com.devnotfound.talenthub.controller;

import com.devnotfound.talenthub.dto.LoginRequestDTO;
import com.devnotfound.talenthub.dto.TokenResponseDTO;
import com.devnotfound.talenthub.service.ClienteService;
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
    private final ClienteService clienteService;

    @PostMapping("/gerarToken")
    @Operation(summary = "Gerar Token",
            description = "Gera token de acesso à API para USER ou CUSTOMER")
    public ResponseEntity<?> gerarToken(
            @RequestBody @Valid LoginRequestDTO loginRequestDTO) {

        try {

            String origem = loginRequestDTO.origem();

            if (origem == null || origem.isBlank()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Origem inválida!");
            }

            boolean authenticated;

            if ("USER".equalsIgnoreCase(origem)) {

                authenticated = userService.authenticate(
                        loginRequestDTO.email(),
                        loginRequestDTO.password()
                );

            } else if ("CUSTOMER".equalsIgnoreCase(origem)) {

                authenticated = clienteService.authenticate(
                        loginRequestDTO.email(),
                        loginRequestDTO.password()
                );

            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Origem inválida!");
            }

            if (!authenticated) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Credenciais inválidas!");
            }

            // Gera token com claim "role"
            String token = tokenService.gerarToken(
                    loginRequestDTO.email(),
                    origem.toUpperCase()
            );

            return ResponseEntity.ok(new TokenResponseDTO(token));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}