package com.example.marketing.mapper;

import com.example.marketing.dto.AlertRuleRequestDTO;
import com.example.marketing.dto.AlertRuleResponseDTO;
import com.example.marketing.model.AlertRule;

public class AlertRuleMapper {

	public static AlertRuleResponseDTO toResponse(AlertRule entity) {
		if (entity == null) return null;

		return AlertRuleResponseDTO.builder()
				.ruleId(entity.getRuleId())
				.campaign(entity.getCampaign() != null ? entity.getCampaign() : null)
				.ruleName(entity.getRuleName())
				.description(entity.getDescription())
				.conditionsJson(entity.getConditionsJson())
				.notificationChannel(entity.getNotificationChannel())
				.active(entity.getIsActive())
				.build();
	}

	public static AlertRule toEntity(AlertRuleRequestDTO request) {
		if (request == null) return null;

		AlertRule entity = new AlertRule();

		entity.setCampaign(request.campaignId());
		entity.setRuleName(request.ruleName());
		entity.setDescription(request.description());
		entity.setConditionsJson(request.conditionsJson());
		entity.setNotificationChannel(request.notificationChannelId());
		entity.setIsActive(request.isActive());

		return entity;
	}

	public static void copyToEntity(AlertRuleRequestDTO request, AlertRule existing) {
		if (request == null || existing == null) return;

		if (request.campaignId() != null)
			existing.setCampaign(request.campaignId());

		if (request.ruleName() != null)
			existing.setRuleName(request.ruleName());

		if (request.description() != null)
			existing.setDescription(request.description());

		if (request.conditionsJson() != null)
			existing.setConditionsJson(request.conditionsJson());

		if (request.notificationChannelId() != null)
			existing.setNotificationChannel(request.notificationChannelId());

		if (request.isActive() != null)
			existing.setIsActive(request.isActive());
	}
}
