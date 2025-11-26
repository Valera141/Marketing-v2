package com.example.marketing.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "authors")

public class Author {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "author_api_id")
	private Integer authorApiId;

	@Column(name = "username", length = 100, nullable = false)
	private String username;

	@Column(name = "is_verified", nullable = false)
	private Boolean isVerified;

	@Column(name = "follower_count")
	private Integer followerCount;

	@Column(name = "is_priority_influencer", nullable = false)
	private Boolean isPriorityInfluencer;

	@Column(name = "first_registration_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	private OffsetDateTime firstRegistrationDate;
}

