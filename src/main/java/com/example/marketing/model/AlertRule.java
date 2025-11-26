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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "alert_rules")
public class AlertRule {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rule_id")
	private Integer ruleId;

	@Column(name = "rule_name", length = 100, nullable = false)
	private String ruleName;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "conditions_json", columnDefinition = "JSONB", nullable = false)
	private String conditionsJson;

	@Column(name = "is_active", nullable = false)
	private Boolean isActive;

	// Relaciones (puedes reemplazar Object por su clase real)
	@ManyToOne
	@JoinColumn(name = "campaign_id", nullable = false)
	private Object campaign;

//	@ManyToOne
//	@JoinColumn(name = "notification_channel_id")
//	private Object notificationChannel;
}

