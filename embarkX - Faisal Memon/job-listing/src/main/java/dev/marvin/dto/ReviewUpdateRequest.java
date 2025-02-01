package dev.marvin.dto;

public record ReviewUpdateRequest(
        Integer rating,
        String comment
) {
}
