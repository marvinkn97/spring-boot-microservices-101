package dev.marvin.controller;

import dev.marvin.dto.ReviewRequest;
import dev.marvin.dto.ReviewResponse;
import dev.marvin.dto.ReviewUpdateRequest;
import dev.marvin.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(@RequestBody ReviewRequest reviewRequest) {
        return ResponseEntity.ok(reviewService.createReview(reviewRequest));
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByOrganization(@PathVariable String organizationId) {
        return ResponseEntity.ok(reviewService.getReviewsByOrganization(organizationId));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable String reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> updateReview(
            @PathVariable String reviewId,
            @RequestBody ReviewUpdateRequest reviewUpdateRequest) {
        return ResponseEntity.ok(reviewService.updateReview(reviewId, reviewUpdateRequest));
    }
}
