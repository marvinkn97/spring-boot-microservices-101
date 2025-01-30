package dev.marvin.controller;

import dev.marvin.dto.JobResponse;
import dev.marvin.service.JobService;
import dev.marvin.util.JobUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("api/v1/jobs")
@RequiredArgsConstructor
@Slf4j
public class JobController {
    private final JobService jobService;

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
}
