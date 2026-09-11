package com.entrygraph.backend.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.entrygraph.backend.entity.Destination;
import com.entrygraph.backend.enums.DestinationCategory;

public interface DestinationRepository
        extends JpaRepository<Destination, UUID>,
                JpaSpecificationExecutor<Destination> {

    List<Destination> findByCategory(DestinationCategory category);

    Page<Destination> findByCategory(
            DestinationCategory category,
            Pageable pageable);

    Page<Destination> findByTags_Id(
            UUID tagId,
            Pageable pageable);

    Page<Destination> findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(
            String name,
            String address,
            Pageable pageable);

    Page<Destination> findByNameContainingIgnoreCaseAndCategoryOrAddressContainingIgnoreCaseAndCategory(
            String name,
            DestinationCategory nameCategory,
            String address,
            DestinationCategory addressCategory,
            Pageable pageable);
}
