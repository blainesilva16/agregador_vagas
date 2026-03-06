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
    public ResponseEntity<TokenResponseDTO> gerarToken(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {

        String origem = loginRequestDTO.origem();

        if (origem == null || (!"USER".equalsIgnoreCase(origem) && !"CLIENTE".equalsIgnoreCase(origem))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new TokenResponseDTO(null, null, null, "Origem inválida! Use USER ou CLIENTE."));
        }

        String origemUpper = origem.toUpperCase();

        Long id;
        String name;
        String email = loginRequestDTO.email();

        if ("USER".equalsIgnoreCase(origemUpper)) {

            boolean ok = userService.authenticate(email, loginRequestDTO.password());
            if (!ok) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new TokenResponseDTO(null, null, null, "Credenciais inválidas!"));
            }

            var userDto = userService.findByEmail(email);
            id = userDto.id().longValue();
            name = userDto.name();
            email = userDto.email();

        } else {
            
            var cliente = clienteAuthService.authenticate(email, loginRequestDTO.password());
            id = cliente.getId();
            name = cliente.getName();
            email = cliente.getEmail();
        }

        String token = tokenService.gerarToken(email, origemUpper);
        return ResponseEntity.ok(new TokenResponseDTO(token, id, name, email));
    }
}