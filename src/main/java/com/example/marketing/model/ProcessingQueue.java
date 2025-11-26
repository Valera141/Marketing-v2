package com.example.marketing.model;



import java.time.OffsetDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "processing_queue")
public class ProcessingQueue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "queue_id")
	private Integer processingQueueId;

	@Column(name = "job_type", nullable = false)
	private String jobType;

	@Column(name = "resource_fk_id", nullable = false)
	private Integer resourceFkId;

	@Column(name = "status", nullable = false)
	private String status;

	@Column(name = "attempts", nullable = false)
	private Integer attempts;

	@Column(name = "creation_date", nullable = false)
	private OffsetDateTime creationDate;

	@Column(name = "last_attempt")
	private OffsetDateTime lastAttempt;

	@Column(name = "error_message")
	private String errorMessage;
}
