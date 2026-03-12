package com.devnotfound.talenthub.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record CustomerUpdateDTO(
		 @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres.")
	        String name,

	        @Email(message = "Email inválido.")
	        @Size(max = 150, message = "Email deve ter no máximo 150 caracteres.")
	        String email,

	        LocalDate birthdate
	) {}