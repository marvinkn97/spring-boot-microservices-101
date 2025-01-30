package dev.marvin.dto;

import java.math.BigDecimal;

public record JobUpdateRequest(
        String title,
        String description,
        BigDecimal minSalary,
        BigDecimal maxSalary,
        String location
) {
}
