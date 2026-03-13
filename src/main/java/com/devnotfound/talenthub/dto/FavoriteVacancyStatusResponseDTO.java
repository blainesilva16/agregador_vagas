package com.devnotfound.talenthub.dto;

public record FavoriteVacancyStatusResponseDTO(
        Integer crawlerId,
        boolean favorite
) {}