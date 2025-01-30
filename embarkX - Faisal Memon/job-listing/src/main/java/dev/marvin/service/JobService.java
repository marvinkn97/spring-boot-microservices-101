package dev.marvin.service;

import dev.marvin.dto.JobRequest;
import dev.marvin.dto.JobResponse;
import dev.marvin.dto.JobUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface JobService {
    JobResponse createJob(JobRequest jobRequest);
    Collection<JobResponse> getAllJobs();
    Page<JobResponse> getAllJobs(Pageable pageable);
    JobResponse getById(String jobId);
    void delete(String jobId);
    JobResponse update(String jobId, JobUpdateRequest jobUpdateRequest);
}
