package com.devnotfound.talenthub.dto;

public record TokenResponseDTO(
		String token,
        Long id,
        String name,
        String email
) {}