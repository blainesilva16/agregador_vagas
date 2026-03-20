package com.devnotfound.talenthub.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FavoriteVacancyResponseDTO(
        Integer crawlerId,
        String title,
        String companyName,
        String cityName,
        String ufAbrev,
        String techLevel,
        String hiringType,
        String workMode,
        String plataform,
        BigDecimal salaryRange,
        String postingLink,
        LocalDateTime creationDate
) {}