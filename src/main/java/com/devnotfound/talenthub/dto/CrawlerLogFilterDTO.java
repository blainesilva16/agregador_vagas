package com.devnotfound.talenthub.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CrawlerLogFilterDTO(
   String plataform,
   Integer positionId,
   Integer techId,
   LocalDateTime startDate,
   LocalDateTime endDate,
   String ufAbrev,
   String cityName,
   String hiringType,
   String techLevel,
   BigDecimal salaryMin,
   BigDecimal salaryMax
) {}
