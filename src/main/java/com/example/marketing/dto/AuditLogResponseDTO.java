package com.example.marketing.dto;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogResponseDTO {

	@JsonProperty("log_id")
	private Integer logId;

	@JsonProperty("user_id")
	private Integer userId;

	private String action;

	@JsonProperty("details")
	private String detailsJson;

	@JsonProperty("log_date")
	private OffsetDateTime logDate;

}
