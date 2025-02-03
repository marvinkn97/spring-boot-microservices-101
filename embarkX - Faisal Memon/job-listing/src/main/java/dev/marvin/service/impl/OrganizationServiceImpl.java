package dev.marvin.service.impl;

import dev.marvin.dto.OrganizationRequest;
import dev.marvin.dto.OrganizationResponse;
import dev.marvin.dto.OrganizationUpdateRequest;
import dev.marvin.exception.BadRequestException;
import dev.marvin.exception.DuplicateResourceException;
import dev.marvin.exception.ResourceNotFoundException;
import dev.marvin.model.Organization;
import dev.marvin.repository.OrganizationRepository;
import dev.marvin.service.OrganizationService;
import dev.marvin.util.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;

    @Override
    public OrganizationResponse createOrganization(OrganizationRequest organizationRequest) {
        log.info("Creating a new organization: {}", organizationRequest);
        try {
            Organization organization = Mapper.mapToEntity(organizationRequest);
            Organization savedOrganization = organizationRepository.save(organization);
            log.info("Organization saved: {}", savedOrganization);
            return Mapper.mapToDto(savedOrganization);
        } catch (DataIntegrityViolationException e) {
            log.error("Error occurred: {}", e.getMessage());
            if (e.getMessage().contains("name")) {
                throw new DuplicateResourceException("Organization with given name already exists");
            }
            throw new RuntimeException("Unexpected error occurred while saving job", e);
        }
    }

    @Override
    public Collection<OrganizationResponse> getAllOrganizations() {
        log.info("Fetching all organizations...");
        return organizationRepository.findAll().stream()
                .map(Mapper::mapToDto)
                .toList();
    }

    @Override
    public Page<OrganizationResponse> getAllOrganizations(Pageable pageable) {
        log.info("Fetching paginated organizations...");
        Page<Organization> page = organizationRepository.findAll(pageable);
        List<OrganizationResponse> organizations = page.getContent().stream()
                .map(Mapper::mapToDto)
                .toList();
        return new PageImpl<>(organizations, pageable, page.getTotalElements());
    }

    @Override
    public OrganizationResponse getById(String organizationId) {
        log.info("Fetching organization with id, {}", organizationId);
        return organizationRepository.getById(organizationId)
                .map(Mapper::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Organization with given id not found"));
    }

    @Override
    public void delete(String organizationId) {
        log.info("Deleting organization with id, {}", organizationId);
        organizationRepository.getById(organizationId)
                .ifPresentOrElse(org -> {
                            org.setIsDeleted(true);
                            organizationRepository.save(org);
                        },
                        () -> {
                            throw new ResourceNotFoundException("Organization with given id not found");
                        });
    }

    @Override
    public OrganizationResponse update(String organizationId, OrganizationUpdateRequest organizationUpdateRequest) {
        log.info("Updating organization with id, {}", organizationId);
        return organizationRepository.getById(organizationId)
                .map(org -> {
                    String newName = organizationUpdateRequest.name();
                    String newDesc = organizationUpdateRequest.description();
                    boolean changes = false;
                    if (StringUtils.hasText(newName) && !org.getName().equals(newName)) {
                        org.setName(newName);
                        changes = true;
                    }
                    if (StringUtils.hasText(newDesc) && !org.getDescription().equals(newDesc)) {
                        org.setDescription(newDesc);
                        changes = true;
                    }
                    if (!changes) {
                        log.info("No changes found for organization with id: {}", organizationId);
                        throw new BadRequestException("No changes found");
                    }
                    Organization updatedOrganization = organizationRepository.save(org);
                    return Mapper.mapToDto(updatedOrganization);
                }).orElseThrow(() -> new ResourceNotFoundException("Organization with given id not found"));
    }
}
