package dev.marvin.controller;

import dev.marvin.dto.JobRequest;
import dev.marvin.dto.JobResponse;
import dev.marvin.dto.JobUpdateRequest;
import dev.marvin.service.JobService;
import dev.marvin.util.JobUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("api/v1/jobs")
@RequiredArgsConstructor
@Slf4j
public class JobController {
    private final JobService jobService;

    @PostMapping
    public ResponseEntity<JobResponse> create(@Valid @RequestBody JobRequest jobRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobService.createJob(jobRequest));
    }

    @GetMapping
    public ResponseEntity<Collection<JobResponse>> getAll() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<JobResponse>> getAll(@RequestParam(name = "pageSize", required = false) Integer pageSize, @RequestParam(name = "pageNumber", required = false) Integer pageNumber) {
        int pSize = pageSize != null ? pageSize : JobUtils.PAGE_SIZE;
        int pNo = pageNumber != null ? pageNumber : JobUtils.PAGE_NUMBER;
        Pageable pageable = PageRequest.of(pNo, pSize, Sort.by(Sort.Direction.ASC, JobUtils.SORT_COLUMN));
        return ResponseEntity.ok(jobService.getAllJobs(pageable));
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<JobResponse> getOne(@PathVariable("jobId") String jobId) {
        return ResponseEntity.ok(jobService.getById(jobId));
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<String> delete(@PathVariable("jobId") String jobId) {
        jobService.delete(jobId);
        return ResponseEntity.ok("Job deleted successfully");
    }

    @PatchMapping("/{jobId}")
    public ResponseEntity<JobResponse> update(@PathVariable("jobId") String jobId, @RequestBody JobUpdateRequest jobUpdateRequest) {
        return ResponseEntity.ok(jobService.update(jobId, jobUpdateRequest));
    }
}
