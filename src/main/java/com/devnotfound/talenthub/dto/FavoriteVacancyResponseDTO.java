package com.devnotfound.talenthub.dto;

import java.time.LocalDateTime;

public record FavoriteVacancyResponseDTO(
        Long crawlerId,
        String title,
        String companyName,
        String postingLink,
        LocalDateTime creationDate
) {}