package com.entrygraph.backend.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.entrygraph.backend.dto.request.CreateDestinationRequest;
import com.entrygraph.backend.dto.response.DestinationResponse;
import com.entrygraph.backend.dto.response.TagResponse;
import com.entrygraph.backend.entity.Destination;
import com.entrygraph.backend.entity.Tag;
import com.entrygraph.backend.enums.DestinationCategory;
import com.entrygraph.backend.enums.DestinationStatus;
import com.entrygraph.backend.exception.DestinationNotFoundException;
import com.entrygraph.backend.exception.TagNotFoundException;
import com.entrygraph.backend.repository.DestinationRepository;
import com.entrygraph.backend.repository.TagRepository;
import com.entrygraph.backend.specification.DestinationSpecification;

@Service
public class DestinationService {

    private final DestinationRepository destinationRepository;
    private final TagRepository tagRepository;

    public DestinationService(
            DestinationRepository destinationRepository,
            TagRepository tagRepository) {
        this.destinationRepository = destinationRepository;
        this.tagRepository = tagRepository;
    }

    public List<DestinationResponse> getAllDestinations() {
        return destinationRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Page<DestinationResponse> getDestinations(Pageable pageable) {
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
        return destinationRepository
                .findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(
                        search,
                        search,
                        Pageable.unpaged())
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Page<DestinationResponse> searchDestinations(
            String search,
            Pageable pageable) {

        return destinationRepository
                .findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(
                        search,
                        search,
                        pageable)
                .map(this::toResponse);
    }

    public List<DestinationResponse> searchDestinations(
            String search,
            DestinationCategory category) {

        return destinationRepository
                .findByNameContainingIgnoreCaseAndCategoryOrAddressContainingIgnoreCaseAndCategory(
                        search,
                        category,
                        search,
                        category,
                        Pageable.unpaged())
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Page<DestinationResponse> searchDestinations(
            String search,
            DestinationCategory category,
            Pageable pageable) {

        return destinationRepository
                .findByNameContainingIgnoreCaseAndCategoryOrAddressContainingIgnoreCaseAndCategory(
                        search,
                        category,
                        search,
                        category,
                        pageable)
                .map(this::toResponse);
    }

    public Page<DestinationResponse> getDestinationsByTag(
            UUID tagId,
            Pageable pageable) {

        return destinationRepository
                .findByTags_Id(tagId, pageable)
                .map(this::toResponse);
    }

    public Page<DestinationResponse> filterDestinations(
            String search,
            DestinationCategory category,
            DestinationStatus status,
            UUID tagId,
            Pageable pageable) {

        return destinationRepository.findAll(
                DestinationSpecification.search(search)
                        .and(DestinationSpecification.hasCategory(category))
                        .and(DestinationSpecification.hasStatus(status))
                        .and(DestinationSpecification.hasTag(tagId)),
                pageable
        ).map(this::toResponse);
    }

    public DestinationResponse getDestinationById(UUID id) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() ->
                        new DestinationNotFoundException(
                                "Destination not found with id: " + id));

        return toResponse(destination);
    }

    @Transactional
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
        destination.setTags(resolveTags(request.getTagIds()));

        Destination saved = destinationRepository.save(destination);

        return toResponse(saved);
    }

    @Transactional
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
        destination.setTags(resolveTags(request.getTagIds()));

        Destination updated = destinationRepository.save(destination);

        return toResponse(updated);
    }

    @Transactional
    public void deleteDestination(UUID id) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() ->
                        new DestinationNotFoundException(
                                "Destination not found with id: " + id));

        destinationRepository.delete(destination);
    }

    private Set<Tag> resolveTags(Set<UUID> tagIds) {

        if (tagIds == null || tagIds.isEmpty()) {
            return new HashSet<>();
        }

        return tagIds.stream()
                .map(tagId -> tagRepository.findById(tagId)
                        .orElseThrow(() ->
                                new TagNotFoundException(
                                        "Tag not found with id: " + tagId)))
                .collect(Collectors.toSet());
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

        Set<TagResponse> tags = destination.getTags()
                .stream()
                .map(this::toTagResponse)
                .collect(Collectors.toSet());

        response.setTags(tags);

        return response;
    }

    private TagResponse toTagResponse(Tag tag) {

        TagResponse response = new TagResponse();

        response.setId(tag.getId());
        response.setName(tag.getName());
        response.setCreatedAt(tag.getCreatedAt());

        return response;
    }
}
