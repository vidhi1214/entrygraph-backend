package com.entrygraph.backend.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.entrygraph.backend.dto.request.CreateDestinationRequest;
import com.entrygraph.backend.dto.response.DestinationResponse;
import com.entrygraph.backend.entity.Destination;
import com.entrygraph.backend.enums.DestinationStatus;
import com.entrygraph.backend.repository.DestinationRepository;

@Service
public class DestinationService {

    private final DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    public List<DestinationResponse> getAllDestinations() {
        return destinationRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public DestinationResponse createDestination(CreateDestinationRequest request) {

        Destination destination = new Destination();

        // Generate UUID in application
        destination.setId(UUID.randomUUID());

        destination.setName(request.getName());
        destination.setAddress(request.getAddress());
        destination.setLatitude(request.getLatitude());
        destination.setLongitude(request.getLongitude());
        destination.setCategory(request.getCategory());
        destination.setDescription(request.getDescription());
        destination.setStatus(DestinationStatus.ACTIVE);

        Destination saved = destinationRepository.save(destination);

        return toResponse(saved);
    }

    private DestinationResponse toResponse(Destination destination) {

        DestinationResponse response = new DestinationResponse();

        response.setId(destination.getId());
        response.setName(destination.getName());
        response.setAddress(destination.getAddress());
        response.setLatitude(destination.getLatitude());
        response.setLongitude(destination.getLongitude());
        response.setCategory(destination.getCategory());
        response.setDescription(destination.getDescription());
        response.setStatus(destination.getStatus());
        response.setCreatedAt(destination.getCreatedAt());

        return response;
    }
}