package com.devnotfound.talenthub.dto;

import java.time.LocalDate;

public record CustomerResponseDTO(
		Integer id,
		String name,
		String email,
		LocalDate birthdate
) {}