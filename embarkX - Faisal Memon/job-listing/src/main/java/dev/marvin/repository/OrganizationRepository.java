package dev.marvin.repository;

import dev.marvin.model.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
    @Query("SELECT o FROM Organization o WHERE o.isDeleted = false")
    Page<Organization> getAll(Pageable pageable);

    @Query("SELECT o FROM Organization o WHERE o.orgId = :orgId AND o.isDeleted = false")
    Optional<Organization> getById(@Param("orgId") String orgId);

}
