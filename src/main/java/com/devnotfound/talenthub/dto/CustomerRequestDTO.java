package com.devnotfound.talenthub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerRequestDTO(
	    @NotBlank(message = "Nome é obrigatório")
	    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
	    String name,

	    @NotBlank(message = "Email é obrigatório")
	    @Email(message = "Email inválido")
	    @Size(max = 150, message = "Email deve ter no máximo 150 caracteres")
	    String email,

	    @NotBlank(message = "Senha é obrigatória")
	    @Size(min = 6, max = 100, message = "Senha deve ter entre 6 e 100 caracteres")
	    String password
	) {}