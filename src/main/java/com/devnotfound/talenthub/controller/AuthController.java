package com.devnotfound.talenthub.controller;

import com.devnotfound.talenthub.constants.SystemConstants;
import com.devnotfound.talenthub.dto.LoginRequestDTO;
import com.devnotfound.talenthub.dto.TokenResponseDTO;
import com.devnotfound.talenthub.service.CustomerAuthService;
import com.devnotfound.talenthub.service.TokenService;
import com.devnotfound.talenthub.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Responsible for customer and user authentication.")
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;
    private final UserService userService;
    private final CustomerAuthService customerAuthService;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Authenticates USER or CUSTOMER and returns an access token.")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {

        String origin = loginRequestDTO.origem();

        if (!"USER".equalsIgnoreCase(origin) && !"CUSTOMER".equalsIgnoreCase(origin)) {
            throw new IllegalArgumentException(SystemConstants.INVALID_ORIGIN);
        }

        String originUpper = origin.toUpperCase(); 

        Integer id;
        String name;
        String email = loginRequestDTO.email();

        if ("USER".equals(originUpper)) {
            boolean authenticated = userService.authenticate(email, loginRequestDTO.password());

            if (!authenticated) {
                throw new BadCredentialsException(SystemConstants.INVALID_CREDENTIALS);
            }

            var userDto = userService.findByEmail(email);
            id = userDto.id().intValue();
            name = userDto.name();
            email = userDto.email();

        } else {
            var customer = customerAuthService.authenticate(email, loginRequestDTO.password());
            id = customer.getId();
            name = customer.getName();
            email = customer.getEmail();
        }

        String token = tokenService.generateToken(email, originUpper);

        return ResponseEntity.ok(new TokenResponseDTO(token, id, name, email));
    }
}