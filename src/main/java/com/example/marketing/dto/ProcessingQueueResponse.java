package com.example.marketing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
@Builder
public class ProcessingQueueResponse {

    @JsonProperty("identifier queue")
    private Integer queueId;

    @JsonProperty("job type")
    private String jobType;

    @JsonProperty("resource")
    private Integer resourceFkId;

    private String status;
    private Integer attempts;

    @JsonProperty("creation date")
    private OffsetDateTime creationDate;

    @JsonProperty("last attempt")
    private OffsetDateTime lastAttempt;

    @JsonProperty("error message")
    private String errorMessage;
}