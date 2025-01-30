package dev.marvin.controller;

import dev.marvin.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/jobs")
@RequiredArgsConstructor
@Slf4j
public class JobController {
    private final JobService jobService;
}
