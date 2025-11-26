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
@Table(name = "generated_alerts")
public class GeneratedAlert {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "alert_id")
	private Integer alertId;

	@Column(name = "generation_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	private OffsetDateTime generationDate;

	@Column(name = "status", length = 50)
	private String status;

	// Relaciones
	@ManyToOne
	@JoinColumn(name = "rule_id", nullable = false)
	private AlertRule alertRule;

	@ManyToOne
	@JoinColumn(name = "publication_api_id", nullable = false)
	private Publication publication;
}
