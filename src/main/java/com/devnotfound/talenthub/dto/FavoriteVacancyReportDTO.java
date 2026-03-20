package com.devnotfound.talenthub.dto;

import java.time.LocalDateTime;
import java.util.List;

public record FavoriteVacancyReportDTO(
        String customerName,
        String customerEmail,
        LocalDateTime generatedAt,
        Integer totalFavorites,
        List<FavoriteVacancyReportItemDTO> favorites
) {}