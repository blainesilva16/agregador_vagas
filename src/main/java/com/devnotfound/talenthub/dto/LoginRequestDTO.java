package com.devnotfound.talenthub.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank(message = "Email é obrigatório.")
        String email,

        @NotBlank(message = "Senha é obrigatória.")
        String password,

        @NotBlank(message = "Origem é obrigatória.")
        String origem
) {}