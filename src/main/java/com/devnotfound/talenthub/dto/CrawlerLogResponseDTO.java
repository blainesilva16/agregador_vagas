package com.devnotfound.talenthub.dto;

import com.devnotfound.talenthub.entity.enums.WorkMode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CrawlerLogResponseDTO(
        Integer id,
        String title,
        String companyName,
        String cityName,
        String ufAbrev,
        WorkMode workMode,
        String hiringType,
        String techLevel,
        BigDecimal salaryRange,
        String platform,
        LocalDateTime creationDate
) {}
