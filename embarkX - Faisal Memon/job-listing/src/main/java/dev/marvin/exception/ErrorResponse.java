package dev.marvin.exception;

public record ErrorResponse(
        String status,
        String description
) {
}
