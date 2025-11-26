package com.example.marketing.mapper;

import com.example.marketing.dto.GeneratedAlertRequestDTO;
import com.example.marketing.dto.GeneratedAlertResponseDTO;
import com.example.marketing.model.GeneratedAlert;

public class GeneratedAlertMapper {

	public static GeneratedAlertResponseDTO toResponse(GeneratedAlert entity) {
		if (entity == null) return null;

		return GeneratedAlertResponseDTO.builder()
				.alertId(entity.getAlertId())
				.ruleId(entity.getRuleId())
				.publicationApiId(entity.getPublicationApiId())
				.status(entity.getStatus())
				.generationDate(entity.getGenerationDate())
				.build();
	}

	public static GeneratedAlert toEntity(GeneratedAlertRequestDTO request) {
		if (request == null) return null;

		GeneratedAlert entity = new GeneratedAlert();
		entity.setRuleId(request.ruleId());
		entity.setPublicationApiId(request.publicationApiId());
		entity.setStatus(request.status() != null ? request.status() : "Pending");

		return entity;
	}

	public static void copyToEntity(GeneratedAlertRequestDTO request, GeneratedAlert existing) {
		if (request == null || existing == null) return;

		if (request.ruleId() != null)
			existing.setRuleId(request.ruleId());

		if (request.publicationApiId() != null)
			existing.setPublicationApiId(request.publicationApiId());

		if (request.status() != null)
			existing.setStatus(request.status());
	}
}
