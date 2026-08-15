package com.entrygraph.backend.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entrygraph.backend.entity.Destination;
import com.entrygraph.backend.enums.DestinationCategory;

public interface DestinationRepository extends JpaRepository<Destination, UUID> {

    List<Destination> findByCategory(DestinationCategory category);

    List<Destination> findByNameContainingIgnoreCase(String name);

    List<Destination> findByNameContainingIgnoreCaseAndCategory(
            String name,
            DestinationCategory category);
}