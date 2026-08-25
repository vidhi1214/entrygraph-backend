package com.entrygraph.backend.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.entrygraph.backend.dto.request.CreateDestinationRequest;
import com.entrygraph.backend.dto.response.DestinationResponse;
import com.entrygraph.backend.entity.Destination;
import com.entrygraph.backend.enums.DestinationCategory;
import com.entrygraph.backend.enums.DestinationStatus;
import com.entrygraph.backend.exception.DestinationNotFoundException;
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

    public Page<DestinationResponse> getDestinations(
            Pageable pageable) {

        return destinationRepository.findAll(pageable)
                .map(this::toResponse);
    }

    public List<DestinationResponse> getDestinationsByCategory(
            DestinationCategory category) {

        return destinationRepository.findByCategory(category)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Page<DestinationResponse> getDestinationsByCategory(
            DestinationCategory category,
            Pageable pageable) {

        return destinationRepository
                .findByCategory(category, pageable)
                .map(this::toResponse);
    }

    public List<DestinationResponse> searchDestinations(String search) {

        return destinationRepository.findByNameContainingIgnoreCase(search)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Page<DestinationResponse> searchDestinations(
            String search,
            Pageable pageable) {

        return destinationRepository
                .findByNameContainingIgnoreCase(search, pageable)
                .map(this::toResponse);
    }

    public List<DestinationResponse> searchDestinations(
            String search,
            DestinationCategory category) {

        return destinationRepository
                .findByNameContainingIgnoreCaseAndCategory(search, category)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Page<DestinationResponse> searchDestinations(
            String search,
            DestinationCategory category,
            Pageable pageable) {

        return destinationRepository
                .findByNameContainingIgnoreCaseAndCategory(
                        search,
                        category,
                        pageable)
                .map(this::toResponse);
    }

    public DestinationResponse getDestinationById(UUID id) {

        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() ->
                        new DestinationNotFoundException(
                                "Destination not found with id: " + id));

        return toResponse(destination);
    }

    public DestinationResponse createDestination(
            CreateDestinationRequest request) {

        Destination destination = new Destination();

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

    public DestinationResponse updateDestination(
            UUID id,
            CreateDestinationRequest request) {

        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() ->
                        new DestinationNotFoundException(
                                "Destination not found with id: " + id));

        destination.setName(request.getName());
        destination.setAddress(request.getAddress());
        destination.setLatitude(request.getLatitude());
        destination.setLongitude(request.getLongitude());
        destination.setCategory(request.getCategory());
        destination.setDescription(request.getDescription());

        Destination updated = destinationRepository.save(destination);

        return toResponse(updated);
    }

    public void deleteDestination(UUID id) {

        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() ->
                        new DestinationNotFoundException(
                                "Destination not found with id: " + id));

        destinationRepository.delete(destination);
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