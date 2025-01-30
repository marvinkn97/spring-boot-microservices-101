package dev.marvin.serviceImpl;

import dev.marvin.dto.JobRequest;
import dev.marvin.dto.JobResponse;
import dev.marvin.exception.DuplicateResourceException;
import dev.marvin.model.Job;
import dev.marvin.repository.JobRepository;
import dev.marvin.service.JobService;
import dev.marvin.util.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;

    @Override
    public JobResponse createJob(JobRequest jobRequest) {
        log.info("Saving a new job: {}", jobRequest);
        try {
            Job job = Mapper.mapToEntity(jobRequest);
            Job savedJob = jobRepository.save(job);
            log.info("Job saved: {}", savedJob);
            return Mapper.mapToDto(savedJob);
        } catch (DataIntegrityViolationException e) {
            log.error("error occurred: {}", e.getMessage());
            if (e.getMessage().contains("title")) {
                throw new DuplicateResourceException("Job with given title already exists");
            }
            throw new RuntimeException("Unexpected error occurred while saving job", e);
        }
    }

    @Override
    public Collection<JobResponse> getAllJobs() {
        log.info("Fetching all records...");
        return jobRepository.findAll().stream()
                .map(Mapper::mapToDto)
                .toList();
    }

    @Override
    public Page<JobResponse> getAllJobs(Pageable pageable) {
        log.info("Fetching paginated records...");
        Page<Job> page = jobRepository.getAll(pageable);
        List<JobResponse> jobs = page.getContent().stream()
                .map(Mapper::mapToDto)
                .toList();
        return new PageImpl<>(jobs, pageable, page.getTotalElements());
    }
}