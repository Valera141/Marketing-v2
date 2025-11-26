package com.example.marketing.mapper;

import com.example.marketing.dto.ProcessingQueueRequest;
import com.example.marketing.dto.ProcessingQueueResponse;
import com.example.marketing.model.ProcessingQueue;
import java.time.OffsetDateTime;

public class ProcessingQueueMapper {

    public static ProcessingQueueResponse toResponse(ProcessingQueue entity) {
        if (entity == null) return null;
        return ProcessingQueueResponse.builder()
                .queueId(entity.getProcessingQueueId())
                .jobType(entity.getJobType())
                .resourceFkId(entity.getResourceFkId())
                .status(entity.getStatus())
                .attempts(entity.getAttempts())
                .creationDate(entity.getCreationDate())
                .lastAttempt(entity.getLastAttempt())
                .errorMessage(entity.getErrorMessage())
                .build();
    }

    public static ProcessingQueue toEntity(ProcessingQueueRequest request) {
        if (request == null) return null;
        ProcessingQueue entity = new ProcessingQueue();
        entity.setJobType(request.jobType());
        entity.setResourceFkId(request.resourceFkId());
        
        // Default values set by the server
        entity.setStatus("Pending");
        entity.setAttempts(0);
        entity.setCreationDate(OffsetDateTime.now());
        
        return entity;
    }
}