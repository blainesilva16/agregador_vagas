package com.devnotfound.talenthub.dto;

import java.time.LocalDateTime;

public record FavoriteVacancyResponseDTO(
        Integer crawlerId,
        String title,
        String companyName,
        String postingLink,
        LocalDateTime creationDate
) {}