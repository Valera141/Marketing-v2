package com.example.marketing.dto;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorResponseDTO {

	@JsonProperty("identificator")
	private Integer authorApiId;

	private String username;

	@JsonProperty("it is verified")
	private Boolean verified;

	@JsonProperty("number of followers")
	private Integer follower;

	@JsonProperty("priority Influencer")
	private Boolean priority;

	@JsonProperty("first registration")
	private OffsetDateTime firstRegistrationDate;

}
