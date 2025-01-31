package dev.marvin.dto;

import jakarta.validation.constraints.NotBlank;

public record OrganizationRequest(
        @NotBlank(message = "organization name must be provided")
        String name,
        @NotBlank(message = "organization description must be provided")
        String description
) {
}
