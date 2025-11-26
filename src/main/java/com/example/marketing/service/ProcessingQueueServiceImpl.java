package com.example.marketing.service;

import com.example.marketing.dto.ProcessingQueueRequest;
import com.example.marketing.dto.ProcessingQueueResponse;
import com.example.marketing.dto.ProcessingQueueStatusUpdateRequest;
import com.example.marketing.mapper.ProcessingQueueMapper;
import com.example.marketing.model.ProcessingQueue;
import com.example.marketing.repository.ProcessingQueueRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ProcessingQueueServiceImpl implements ProcessingQueueService {

    private final ProcessingQueueRepository queueRepository;

    @Override
    public ProcessingQueueResponse createJob(ProcessingQueueRequest request) {
        ProcessingQueue newJob = ProcessingQueueMapper.toEntity(request);
        return ProcessingQueueMapper.toResponse(queueRepository.save(newJob));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProcessingQueueResponse> findAll(Pageable pageable) {
        return queueRepository.findAll(pageable).map(ProcessingQueueMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ProcessingQueueResponse findById(Integer queueId) {
        return queueRepository.findById(queueId)
            .map(ProcessingQueueMapper::toResponse)
            .orElseThrow(() -> new EntityNotFoundException("Trabajo en cola no encontrado con ID: " + queueId));
    }

    @Override
    public ProcessingQueueResponse updateJobStatus(Integer queueId, ProcessingQueueStatusUpdateRequest request) {
        ProcessingQueue job = queueRepository.findById(queueId)
            .orElseThrow(() -> new EntityNotFoundException("Trabajo en cola no encontrado con ID: " + queueId));

        job.setStatus(request.status());
        job.setLastAttempt(OffsetDateTime.now());
        job.setAttempts(job.getAttempts() + 1);
        
        if ("Failed".equalsIgnoreCase(request.status())) {
            job.setErrorMessage(request.errorMessage());
        } else {
            job.setErrorMessage(null); // Clear error on success
        }

        return ProcessingQueueMapper.toResponse(queueRepository.save(job));
    }
}