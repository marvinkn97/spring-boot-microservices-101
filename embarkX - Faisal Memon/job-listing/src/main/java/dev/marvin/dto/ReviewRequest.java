package dev.marvin.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ReviewRequest(
        @Min(1) @Max(5)
        Integer rating,

        @NotBlank(message = "review comment must be provided")
        String comment,

        @NotBlank(message = "organization id must be provided")
        String organizationId
) {
}
