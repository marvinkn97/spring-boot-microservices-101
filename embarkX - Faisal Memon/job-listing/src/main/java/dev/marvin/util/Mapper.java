package dev.marvin.util;

import dev.marvin.dto.JobRequest;
import dev.marvin.dto.JobResponse;
import dev.marvin.model.Job;

public class Mapper {

    public static Job mapToEntity(JobRequest jobRequest) {
        return Job.builder()
                .title(jobRequest.title())
                .description(jobRequest.description())
                .minSalary(jobRequest.minSalary())
                .maxSalary(jobRequest.maxSalary())
                .location(jobRequest.location())
                .build();
    }

    public static JobResponse mapToDto(Job job) {
        return new JobResponse(job.getJobId(), job.getTitle(), job.getDescription(), job.getMinSalary(), job.getMaxSalary(), job.getLocation());
    }
}
