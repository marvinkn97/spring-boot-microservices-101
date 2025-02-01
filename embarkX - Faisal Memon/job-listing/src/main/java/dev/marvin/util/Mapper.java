package dev.marvin.util;

import dev.marvin.dto.*;
import dev.marvin.model.Job;
import dev.marvin.model.Organization;
import dev.marvin.model.Review;

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
        return new JobResponse(
                job.getJobId(),
                job.getTitle(),
                job.getDescription(),
                job.getMinSalary(),
                job.getMaxSalary(),
                job.getLocation());
    }

    public static Organization mapToEntity(OrganizationRequest request) {
        return Organization.builder()
                .name(request.name())
                .description(request.description())
                .build();
    }

    public static OrganizationResponse mapToDto(Organization organization) {
        return new OrganizationResponse(
                organization.getOrgId(),
                organization.getName(),
                organization.getDescription()
        );
    }

    public static ReviewResponse toDto(Review review) {
        return new ReviewResponse(
                review.getReviewId(),
                null,
                review.getRating(),
                review.getComment(),
                review.getOrganization().getOrgId()
        );
    }
}
