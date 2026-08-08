package com.entrygraph.backend.controller;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import com.entrygraph.backend.dto.request.CreateDestinationRequest;
import com.entrygraph.backend.dto.response.DestinationResponse;
import com.entrygraph.backend.service.DestinationService;

@RestController
@RequestMapping("/api/v1/destinations")
public class DestinationController {

    private final DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping
    public List<DestinationResponse> getAllDestinations() {
        return destinationService.getAllDestinations();
    }

    @GetMapping("/{id}")
    public DestinationResponse getDestinationById(@PathVariable UUID id) {
        return destinationService.getDestinationById(id);
    }

    @PostMapping
    public DestinationResponse createDestination(
            @Valid @RequestBody CreateDestinationRequest request) {

        return destinationService.createDestination(request);
    }

    @PutMapping("/{id}")
    public DestinationResponse updateDestination(
            @PathVariable UUID id,
            @Valid @RequestBody CreateDestinationRequest request) {

        return destinationService.updateDestination(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteDestination(@PathVariable UUID id) {
        destinationService.deleteDestination(id);
    }
}