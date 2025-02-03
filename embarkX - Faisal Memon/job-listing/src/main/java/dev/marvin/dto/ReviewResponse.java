package dev.marvin.dto;

public record ReviewResponse(
        String reviewId,
        String reviewerId,
        Integer rating,
        String comment,
        String organizationName
) {
}
