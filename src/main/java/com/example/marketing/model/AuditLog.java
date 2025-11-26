package com.example.marketing.model;



import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "audit_logs")
public class AuditLog {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "log_id")
	private Integer logId;

	@Column(name = "action", length = 255, nullable = false)
	private String action;

	@Column(name = "details", columnDefinition = "JSONB")
	private String details;

	@Column(name = "log_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	private OffsetDateTime logDate;

	// user_id puede ser NULL
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}

