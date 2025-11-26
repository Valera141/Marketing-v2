package com.example.marketing.repository;

import com.example.marketing.model.ProcessingQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProcessingQueueRepository extends JpaRepository<ProcessingQueue, Integer> {
    
    /**
     * Finds a list of pending jobs, ordered by creation date to process the oldest first.
     * @param status The status to search for (e.g., "Pending").
     * @return A list of pending jobs.
     */
    List<ProcessingQueue> findTop10ByStatusOrderByCreationDateAsc(String status);
}