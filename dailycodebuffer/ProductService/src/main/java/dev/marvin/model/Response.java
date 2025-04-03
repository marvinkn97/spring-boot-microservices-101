package dev.marvin.model;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Response(LocalDateTime timestamp, Integer status, String reason, Object payload) {
}
