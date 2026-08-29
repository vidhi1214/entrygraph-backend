package com.entrygraph.backend.controller;

import java.net.URI;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.entrygraph.backend.dto.request.CreateDestinationRequest;
import com.entrygraph.backend.dto.response.DestinationResponse;
import com.entrygraph.backend.enums.DestinationCategory;
import com.entrygraph.backend.enums.DestinationStatus;
import com.entrygraph.backend.service.DestinationService;

@RestController
@RequestMapping("/api/v1/destinations")
public class DestinationController {

    private final DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping
    public Page<DestinationResponse> getDestinations(
            @RequestParam(required = false) DestinationCategory category,
            @RequestParam(required = false) DestinationStatus status,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) UUID tagId,
            Pageable pageable) {

        return destinationService.filterDestinations(
                search,
                category,
                status,
                tagId,
                pageable);
    }

    @GetMapping("/{id}")
    public DestinationResponse getDestinationById(@PathVariable UUID id) {
        return destinationService.getDestinationById(id);
    }

    @PostMapping
    public ResponseEntity<DestinationResponse> createDestination(
            @Valid @RequestBody CreateDestinationRequest request) {

        DestinationResponse created =
                destinationService.createDestination(request);

        URI location = URI.create(
                "/api/v1/destinations/" + created.getId());

        return ResponseEntity
                .created(location)
                .body(created);
    }

    @PutMapping("/{id}")
    public DestinationResponse updateDestination(
            @PathVariable UUID id,
            @Valid @RequestBody CreateDestinationRequest request) {

        return destinationService.updateDestination(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDestination(
            @PathVariable UUID id) {

        destinationService.deleteDestination(id);

        return ResponseEntity.noContent().build();
    }
}