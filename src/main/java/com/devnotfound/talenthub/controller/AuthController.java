package com.devnotfound.talenthub.controller;

import com.devnotfound.talenthub.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/gerarToken")
    @Operation(summary = "Gerar Token", description = "Rota responsavel por gerar o Token de acesso a API!")
    public ResponseEntity<?> gerarToken(String email, String password) {
        try {
            // aqui precisa implementar a busca de usuario no banco de dados
            if (!(email.equals("string") && password.equals("string"))) {
                return ResponseEntity.notFound().build();
            }

            var lRetorno = tokenService.gerarToken(email);
            return ResponseEntity.ok(lRetorno);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
