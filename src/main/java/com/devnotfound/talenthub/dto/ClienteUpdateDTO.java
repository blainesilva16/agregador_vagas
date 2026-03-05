package com.devnotfound.talenthub.dto;

import com.devnotfound.talenthub.dto.ClienteUpdateDTO;

import java.time.LocalDate;

public record ClienteUpdateDTO(
	    String name,
	    String email,
	    LocalDate birthdate,
	    byte[] photo
	) {}
