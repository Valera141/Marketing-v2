package com.example.marketing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AlertRuleRequestDTO(

		@NotNull(message = "El campaignId es obligatorio")
		Integer campaignId,

		@NotBlank(message = "El nombre de la regla es obligatorio")
		@Size(max = 100)
		String ruleName,

		String description,

		@NotBlank(message = "Las condiciones JSON son obligatorias")
		String conditionsJson,

		Integer notificationChannelId,

		@NotNull(message = "El estado (isActive) es obligatorio")
		Boolean isActive
) {}
