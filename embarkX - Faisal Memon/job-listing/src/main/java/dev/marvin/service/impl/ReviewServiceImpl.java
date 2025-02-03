package dev.marvin.service.impl;

import dev.marvin.dto.ReviewRequest;
import dev.marvin.dto.ReviewResponse;
import dev.marvin.dto.ReviewUpdateRequest;
import dev.marvin.exception.BadRequestException;
import dev.marvin.exception.ResourceNotFoundException;
import dev.marvin.model.Organization;
import dev.marvin.model.Review;
import dev.marvin.repository.OrganizationRepository;
import dev.marvin.repository.ReviewRepository;
import dev.marvin.service.ReviewService;
import dev.marvin.util.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {
    private final OrganizationRepository organizationRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    @Override
    public ReviewResponse createReview(ReviewRequest reviewRequest) {
        log.info("Creating a new review: {}", reviewRequest);
        Organization organization = organizationRepository.getById(reviewRequest.organizationId())
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));

        Review review = Review.builder()
                .rating(reviewRequest.rating())
                .comment(reviewRequest.comment())
                .organization(organization)
                .build();
        Review savedReview = reviewRepository.save(review);
        return Mapper.toDto(savedReview);
    }

    @Override
    public List<ReviewResponse> getReviewsByOrganization(String organizationId) {
        List<Review> reviews = reviewRepository.findByOrganizationId(organizationId);
        return reviews.stream()
                .map(Mapper::toDto)
                .toList();
    }

    @Override
    public void deleteReview(String reviewId) {
        reviewRepository.getByReviewId(reviewId)
                .map(review -> {
                    review.setIsDeleted(true);
                    return reviewRepository.save(review);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));
    }

    @Override
    public ReviewResponse updateReview(String reviewId, ReviewUpdateRequest reviewUpdateRequest) {
        return reviewRepository.getByReviewId(reviewId)
                .map(review -> {
                    Integer newRating = reviewUpdateRequest.rating();
                    String newComment = reviewUpdateRequest.comment();
                    boolean changes = false;

                    if (newRating != null && !newRating.equals(review.getRating())) {
                        review.setRating(newRating);
                        changes = true;
                    }

                    if (StringUtils.hasText(newComment) && newComment.equals(review.getComment())) {
                        review.setComment(newComment);
                        changes = true;
                    }

                    if (!changes) {
                        log.info("No changes found review with id: {}", reviewId);
                        throw new BadRequestException("No changes found");
                    }

                    Review savedReview = reviewRepository.save(review);
                    return Mapper.toDto(savedReview);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));
    }
}
