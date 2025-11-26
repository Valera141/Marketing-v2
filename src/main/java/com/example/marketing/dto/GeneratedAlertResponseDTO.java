package com.example.marketing.dto;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneratedAlertResponseDTO {

	@JsonProperty("alert_id")
	private Integer alertId;

	@JsonProperty("rule_id")
	private Integer ruleId;

	@JsonProperty("publication_id")
	private Integer publicationApiId;

	private String status;

	@JsonProperty("generation_date")
	private OffsetDateTime generationDate;
}
