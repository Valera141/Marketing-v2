package com.example.marketing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertRuleResponseDTO {

	@JsonProperty("identificator")
	private Integer ruleId;

	@JsonProperty("campaign identificator")
	private Integer campaign;

	private String ruleName;

	private String description;

	@JsonProperty("conditions")
	private String conditionsJson;

	@JsonProperty("notification channel")
	private Integer notificationChannel;

	@JsonProperty("active")
	private Boolean active;
}

