package dev.marvin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record JobRequest(
        @NotBlank(message = "job title must be provided")
        String title,

        @NotBlank(message = "job description must be provided")
        String description,

        @NotNull(message = "job minSalary must be provided")
        BigDecimal minSalary,

        @NotNull(message = "job maxSalary must be provided")
        BigDecimal maxSalary,

        @NotBlank(message = "job location must be provided")
        String location
        ) {}
