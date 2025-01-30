package dev.marvin.repository;

import dev.marvin.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    @Query("SELECT j FROM Job j WHERE j.isDeleted = false")
    Page<Job> getAll(Pageable pageable);

    @Query("SELECT j FROM Job j WHERE j.jobId = :jobId AND j.isDeleted = false")
    Optional<Job> getById(@Param("jobId") String jobId);
}
