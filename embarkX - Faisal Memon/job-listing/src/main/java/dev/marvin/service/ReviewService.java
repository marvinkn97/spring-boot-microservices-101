package dev.marvin.service;

import dev.marvin.dto.ReviewRequest;
import dev.marvin.dto.ReviewResponse;
import dev.marvin.dto.ReviewUpdateRequest;

import java.util.List;

public interface ReviewService {
    ReviewResponse createReview(ReviewRequest reviewRequest);
    List<ReviewResponse> getReviewsByOrganization(String organizationId);
    void deleteReview(String reviewId);
    ReviewResponse updateReview(String reviewId, ReviewUpdateRequest reviewUpdateRequest);

}
