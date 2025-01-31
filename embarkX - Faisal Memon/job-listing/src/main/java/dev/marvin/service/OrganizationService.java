package dev.marvin.service;

import dev.marvin.dto.OrganizationRequest;
import dev.marvin.dto.OrganizationResponse;
import dev.marvin.dto.OrganizationUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface OrganizationService {
    OrganizationResponse createOrganization(OrganizationRequest organizationRequest);
    Collection<OrganizationResponse> getAllOrganizations();
    Page<OrganizationResponse> getAllOrganizations(Pageable pageable);
    OrganizationResponse getById(String organizationId);
    void delete(String organizationId);
    OrganizationResponse update(String organizationId, OrganizationUpdateRequest organizationUpdateRequest);
}
