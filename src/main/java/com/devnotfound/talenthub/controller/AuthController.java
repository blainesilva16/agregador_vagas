package com.devnotfound.talenthub.controller;

import com.devnotfound.talenthub.dto.LoginRequestDTO;
import com.devnotfound.talenthub.dto.UserResponseDTO;
import com.devnotfound.talenthub.service.TokenService;
import com.devnotfound.talenthub.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Rota responsavel por gerar o Token!")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @PostMapping("/gerarToken")
    @Operation(summary = "Gerar Token", description = "Rota responsavel por gerar o Token de acesso a API!")
    public ResponseEntity<?> gerarToken(LoginRequestDTO loginRequestDTO) {
        try {
            if ("USER".equalsIgnoreCase(loginRequestDTO.origem())) {
                boolean authenticated = userService.authenticate(loginRequestDTO.email(), loginRequestDTO.password());

                if (!authenticated) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
                }
            } else if ("CUSTOMER".equalsIgnoreCase(loginRequestDTO.origem())) {
                // para quem estiver implementando o customer
            } else {
                return ResponseEntity.status(403).build();
            }

            var lRetorno = tokenService.gerarToken(loginRequestDTO.email());
            return ResponseEntity.ok(lRetorno);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
