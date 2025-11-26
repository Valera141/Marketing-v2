package com.example.marketing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProcessingQueueRequest(
    @NotBlank(message = "El tipo de trabajo (jobType) es obligatorio")
    String jobType,

    @NotNull(message = "El ID del recurso (resourceFkId) es obligatorio")
    Integer resourceFkId
) {}