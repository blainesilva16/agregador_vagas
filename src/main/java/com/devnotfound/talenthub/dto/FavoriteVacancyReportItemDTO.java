package com.devnotfound.talenthub.dto;

import java.math.BigDecimal;
// import java.time.LocalDateTime;

import com.devnotfound.talenthub.entity.enums.WorkMode;

public record FavoriteVacancyReportItemDTO(
        String title,
        String companyName,
        String ufAbrev,
        String hiringType,
        WorkMode workMode,
        BigDecimal salaryRange
) {}