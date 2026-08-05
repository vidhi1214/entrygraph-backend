package com.entrygraph.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.entrygraph.backend.entity.Destination;
import com.entrygraph.backend.repository.DestinationRepository;

@Service
public class DestinationService {

    private final DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    public Destination createDestination(Destination destination) {
        return destinationRepository.save(destination);
    }
}