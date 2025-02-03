package dev.marvin.repository;

import dev.marvin.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("SELECT r FROM Review r WHERE r.reviewId = :reviewId AND r.isDeleted = false")
    Optional<Review> getByReviewId(@Param("reviewId") String reviewId);

    @Query("SELECT r FROM Review r WHERE r.organization.orgId = :orgId AND r.isDeleted = false")
    List<Review> findByOrganizationId(@Param("orgId") String organizationId);
}
