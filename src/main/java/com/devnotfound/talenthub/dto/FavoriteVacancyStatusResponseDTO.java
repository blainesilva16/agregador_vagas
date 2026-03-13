package com.devnotfound.talenthub.dto;

public record FavoriteVacancyStatusResponseDTO(
        Long crawlerId,
        boolean favorite
) {}