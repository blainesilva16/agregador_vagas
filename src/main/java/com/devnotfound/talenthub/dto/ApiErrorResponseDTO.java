package com.devnotfound.talenthub.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiErrorResponseDTO(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        Map<String, String> fieldErrors
) {}