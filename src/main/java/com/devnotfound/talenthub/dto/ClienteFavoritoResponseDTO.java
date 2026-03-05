package com.devnotfound.talenthub.dto;

import java.time.LocalDateTime;

public record ClienteFavoritoResponseDTO(
		String vagaId,
        LocalDateTime createdAt
) {}
