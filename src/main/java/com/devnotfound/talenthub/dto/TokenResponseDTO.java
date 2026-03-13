package com.devnotfound.talenthub.dto;

public record TokenResponseDTO(
		String token,
        Integer id,
        String name,
        String email
) {}