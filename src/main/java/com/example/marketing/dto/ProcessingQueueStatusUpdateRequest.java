package com.example.marketing.dto;

import jakarta.validation.constraints.NotBlank;

public record ProcessingQueueStatusUpdateRequest(
    @NotBlank(message = "El estado no puede estar vacío")
    String status, // e.g., "Completed", "Failed"

    String errorMessage // Opcional, solo para el estado "Failed"
) {}