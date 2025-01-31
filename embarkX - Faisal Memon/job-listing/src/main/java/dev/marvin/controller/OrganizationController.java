package dev.marvin.controller;

import dev.marvin.dto.OrganizationRequest;
import dev.marvin.dto.OrganizationResponse;
import dev.marvin.dto.OrganizationUpdateRequest;
import dev.marvin.service.OrganizationService;
import dev.marvin.util.OrganizationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("api/v1/organizations")
@RequiredArgsConstructor
@Slf4j
public class OrganizationController {
    private final OrganizationService organizationService;

    @PostMapping
    public ResponseEntity<OrganizationResponse> create(@Valid @RequestBody OrganizationRequest organizationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(organizationService.createOrganization(organizationRequest));
    }

    @GetMapping
    public ResponseEntity<Collection<OrganizationResponse>> getAll() {
        return ResponseEntity.ok(organizationService.getAllOrganizations());
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<OrganizationResponse>> getAll(@RequestParam(name = "pageSize", required = false) Integer pageSize, @RequestParam(name = "pageNumber", required = false) Integer pageNumber) {
        int pSize = pageSize != null ? pageSize : OrganizationUtils.PAGE_SIZE;
        int pNo = pageNumber != null ? pageNumber : OrganizationUtils.PAGE_NUMBER;
        Pageable pageable = PageRequest.of(pNo, pSize, Sort.by(Sort.Direction.ASC, OrganizationUtils.SORT_COLUMN));
        return ResponseEntity.ok(organizationService.getAllOrganizations(pageable));
    }

    @GetMapping("/{orgId}")
    public ResponseEntity<OrganizationResponse> getOne(@PathVariable("orgId") String orgId) {
        return ResponseEntity.ok(organizationService.getById(orgId));
    }

    @DeleteMapping("/{orgId}")
    public ResponseEntity<String> delete(@PathVariable("orgId") String orgId) {
        organizationService.delete(orgId);
        return ResponseEntity.ok("Organization deleted successfully");
    }

    @PatchMapping("/{orgId}")
    public ResponseEntity<OrganizationResponse> update(@PathVariable("orgId") String orgId, @RequestBody OrganizationUpdateRequest organizationUpdateRequest) {
        return ResponseEntity.ok(organizationService.update(orgId, organizationUpdateRequest));
    }
}
