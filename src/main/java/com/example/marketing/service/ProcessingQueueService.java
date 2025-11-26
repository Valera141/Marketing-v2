package com.example.marketing.service;

import com.example.marketing.dto.ProcessingQueueRequest;
import com.example.marketing.dto.ProcessingQueueResponse;
import com.example.marketing.dto.ProcessingQueueStatusUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProcessingQueueService {
    ProcessingQueueResponse createJob(ProcessingQueueRequest request);
    Page<ProcessingQueueResponse> findAll(Pageable pageable);
    ProcessingQueueResponse findById(Integer queueId);
    ProcessingQueueResponse updateJobStatus(Integer queueId, ProcessingQueueStatusUpdateRequest request);
    // Note: No general update or delete methods are exposed for standard users
}