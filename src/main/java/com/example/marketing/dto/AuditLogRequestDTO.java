package com.example.marketing.dto;

import jakarta.validation.constraints.NotBlank;

public record AuditLogRequestDTO(

		Integer userId,

		@NotBlank(message = "El campo acción es obligatorio")
		String action,

		String detailsJson

) {}

