package com.devnotfound.talenthub.dto;

import java.time.LocalDateTime;

public record CustomerResponseDTO(
    Integer id,
    String name,
    String email,
    LocalDateTime creationDate
) {}
