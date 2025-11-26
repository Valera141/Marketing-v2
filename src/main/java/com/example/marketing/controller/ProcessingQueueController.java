package com.example.marketing.controller;

import com.example.marketing.dto.ProcessingQueueRequest;
import com.example.marketing.dto.ProcessingQueueResponse;
import com.example.marketing.dto.ProcessingQueueStatusUpdateRequest;
import com.example.marketing.service.ProcessingQueueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/processing-queue")
@RequiredArgsConstructor
@Tag(name = "Processing Queue", description = "API to manage the Job Processing Queue")
public class ProcessingQueueController {

    private final ProcessingQueueService service;

    @Operation(summary = "Enqueue a new job for processing")
    @ApiResponse(responseCode = "201", description = "Job enqueued successfully")
    @PostMapping
    public ResponseEntity<ProcessingQueueResponse> enqueueJob(@Valid @RequestBody ProcessingQueueRequest request) {
        ProcessingQueueResponse createdJob = service.createJob(request);
        return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all jobs from the queue (paginated)")
    @GetMapping
    public Page<ProcessingQueueResponse> getAllJobs(@PageableDefault(size = 20, sort = "creationDate") Pageable pageable) {
        return service.findAll(pageable);
    }

    @Operation(summary = "Get a specific job by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Job found"),
        @ApiResponse(responseCode = "404", description = "Job not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProcessingQueueResponse> getJobById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Update the status of a job (used by workers)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Job status updated"),
        @ApiResponse(responseCode = "404", description = "Job not found")
    })
    @PutMapping("/{id}/status")
    public ResponseEntity<ProcessingQueueResponse> updateJobStatus(@PathVariable Integer id, @Valid @RequestBody ProcessingQueueStatusUpdateRequest request) {
        return ResponseEntity.ok(service.updateJobStatus(id, request));
    }
}