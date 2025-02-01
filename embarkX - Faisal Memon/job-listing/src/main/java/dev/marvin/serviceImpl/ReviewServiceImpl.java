package dev.marvin.serviceImpl;

import dev.marvin.dto.ReviewRequest;
import dev.marvin.dto.ReviewResponse;
import dev.marvin.dto.ReviewUpdateRequest;
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

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {
    private final OrganizationRepository organizationRepository;
    private final ReviewRepository reviewRepository;

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
                .map(review -> new ReviewResponse(
                        review.getReviewId(),
                        null,
                        review.getRating(),
                        review.getComment(),
                        review.getReviewId()
                ))
                .toList();
    }

    @Override
    public void deleteReview(String reviewId) {
        Review review = reviewRepository.getByReviewId(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        review.setIsDeleted(true);
        reviewRepository.save(review);
        //reviewRepository.deleteById(reviewId);
    }

    @Override
    public ReviewResponse updateReview(String reviewId, ReviewUpdateRequest reviewUpdateRequest) {
        Review review = reviewRepository.getByReviewId(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        review.setRating(reviewUpdateRequest.rating());
        review.setComment(reviewUpdateRequest.comment());

        Review updatedReview = reviewRepository.save(review);
        return Mapper.toDto(updatedReview);
    }
}
