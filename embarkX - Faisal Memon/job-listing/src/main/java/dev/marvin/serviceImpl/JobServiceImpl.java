package dev.marvin.serviceImpl;

import dev.marvin.dto.JobRequest;
import dev.marvin.dto.JobResponse;
import dev.marvin.dto.JobUpdateRequest;
import dev.marvin.exception.BadRequestException;
import dev.marvin.exception.DuplicateResourceException;
import dev.marvin.exception.ResourceNotFoundException;
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
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
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
            log.error("Error occurred: {}", e.getMessage());
            if (e.getMessage().contains("title")) {
                throw new DuplicateResourceException("Job with given title already exists");
            }
            throw new RuntimeException("Unexpected error occurred while saving job", e);
        }
    }

    @Override
    public Collection<JobResponse> getAllJobs() {
        log.info("Fetching all jobs...");
        return jobRepository.findAll().stream()
                .map(Mapper::mapToDto)
                .toList();
    }

    @Override
    public Page<JobResponse> getAllJobs(Pageable pageable) {
        log.info("Fetching paginated jobs...");
        Page<Job> page = jobRepository.getAll(pageable);
        List<JobResponse> jobs = page.getContent().stream()
                .map(Mapper::mapToDto)
                .toList();
        return new PageImpl<>(jobs, pageable, page.getTotalElements());
    }

    @Override
    public JobResponse getById(String jobId) {
        log.info("Fetching job with id, {}", jobId);
        return jobRepository.getById(jobId)
                .map(Mapper::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Job with given id not found"));
    }

    @Override
    public void delete(String jobId) {
        log.info("Deleting job with id, {}", jobId);
        jobRepository.getById(jobId)
                .ifPresentOrElse(job -> {
                            job.setIsDeleted(true);
                            jobRepository.save(job);
                        },
                        () -> {
                            throw new ResourceNotFoundException("Job with given id not found");
                        });
    }

    @Override
    public JobResponse update(String jobId, JobUpdateRequest jobUpdateRequest) {
        log.info("Updating job with id, {}", jobId);

        return jobRepository.getById(jobId)
                .map(job -> {
                    String newTitle = jobUpdateRequest.title();
                    String newDescription = jobUpdateRequest.description();
                    BigDecimal newMinSalary = jobUpdateRequest.minSalary();
                    BigDecimal newMaxSalary = jobUpdateRequest.maxSalary();
                    String newLocation = jobUpdateRequest.location();

                    boolean changes = false;
                    if (StringUtils.hasText(newTitle) && !job.getTitle().equals(newTitle)) {
                        job.setTitle(newTitle);
                        changes = true;
                    }
                    if (StringUtils.hasText(newDescription) && !job.getDescription().equals(newDescription)) {
                        job.setDescription(newDescription);
                        changes = true;
                    }
                    if (newMinSalary != null && !job.getMinSalary().equals(newMinSalary)) {
                        job.setMinSalary(newMinSalary);
                        changes = true;
                    }
                    if (newMaxSalary != null && !job.getMaxSalary().equals(newMaxSalary)) {
                        job.setMaxSalary(newMaxSalary);
                        changes = true;
                    }
                    if (StringUtils.hasText(newLocation) && !job.getLocation().equals(newLocation)) {
                        job.setLocation(newLocation);
                        changes = true;
                    }

                    if (!changes) {
                        log.warn("No changes found for job with id: {}", jobId);
                        throw new BadRequestException("No changes found");
                    }

                    Job updatedJob = jobRepository.save(job);
                    return Mapper.mapToDto(updatedJob);

                }).orElseThrow(() -> new ResourceNotFoundException("Job with given id not found"));
    }
}