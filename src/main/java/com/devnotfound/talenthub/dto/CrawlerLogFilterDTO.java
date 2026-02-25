package com.devnotfound.talenthub.dto;

import java.time.LocalDateTime;

public record CrawlerLogFilterDTO(
   String plataform,
   Integer positionId,
   Integer techId,
   LocalDateTime startDate,
   LocalDateTime endDate
) {}
