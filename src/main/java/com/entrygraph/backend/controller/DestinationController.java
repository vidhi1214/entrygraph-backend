package com.entrygraph.backend.controller;

import java.util.List;

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

    @PostMapping
    public DestinationResponse createDestination(
            @RequestBody CreateDestinationRequest request) {

        return destinationService.createDestination(request);
    }
}