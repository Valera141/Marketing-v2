package com.example.marketing.mapper;

import com.example.marketing.dto.AuditLogRequestDTO;
import com.example.marketing.dto.AuditLogResponseDTO;
import com.example.marketing.model.AuditLog;

public class AuditLogMapper {

	public static AuditLogResponseDTO toResponse(AuditLog entity) {
		if (entity == null) return null;

		return AuditLogResponseDTO.builder()
				.logId(entity.getLogId())
				.userId(entity.getUserId())
				.action(entity.getAction())
				.detailsJson(entity.getDetails())
				.logDate(entity.getLogDate())
				.build();
	}

	public static AuditLog toEntity(AuditLogRequestDTO request) {
		if (request == null) return null;

		AuditLog entity = new AuditLog();
		entity.setUserId(request.userId());
		entity.setAction(request.action());
		entity.setDetails(request.detailsJson());

		return entity;
	}

	public static void copyToEntity(AuditLogRequestDTO request, AuditLog existing) {
		if (request == null || existing == null) return;

		if (request.userId() != null)
			existing.setUserId(request.userId());

		if (request.action() != null)
			existing.setAction(request.action());

		if (request.detailsJson() != null)
			existing.setDetails(request.detailsJson());
	}
}
