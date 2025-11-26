package com.example.marketing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;

public record AuthorRequestDTO(

		@NotBlank(message = "El username es obligatorio")
		@Size(max = 100)
		String username,

		@NotNull(message = "El campo isVerified es obligatorio")
		Boolean verified,

		@Min(value = 0, message = "El número de seguidores no puede ser negativo")
		Integer follower,

		@NotNull(message = "El campo isPriorityInfluencer es obligatorio")
		Boolean priority

) {}
