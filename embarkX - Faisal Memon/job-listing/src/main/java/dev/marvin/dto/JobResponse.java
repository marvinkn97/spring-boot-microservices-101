package dev.marvin.dto;

import java.math.BigDecimal;

public record JobResponse(
        String jobId,
        String title,
        String description,
        BigDecimal minSalary,
        BigDecimal maxSalary,
        String location
) {}
