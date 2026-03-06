package com.devnotfound.talenthub.dto;

import java.time.LocalDateTime;

public record VagaFavoritoResponseDTO(
		String vagaId,
        LocalDateTime createdAt
) {}
