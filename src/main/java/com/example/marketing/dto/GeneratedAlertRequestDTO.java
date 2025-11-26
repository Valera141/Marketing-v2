package com.example.marketing.dto;

import jakarta.validation.constraints.NotNull;

public record GeneratedAlertRequestDTO(

		@NotNull(message = "El ruleId es obligatorio")
		Integer ruleId,

		@NotNull(message = "El publicationApiId es obligatorio")
		Integer publicationApiId,

		String status
) {}
