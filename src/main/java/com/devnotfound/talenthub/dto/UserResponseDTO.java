package com.devnotfound.talenthub.dto;

import java.time.LocalDateTime;

public record UserResponseDTO(
    Integer id,
    String name,
    String email,
    LocalDateTime creationDate
) {}
