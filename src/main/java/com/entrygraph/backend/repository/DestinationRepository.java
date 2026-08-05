package com.entrygraph.backend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entrygraph.backend.entity.Destination;

public interface DestinationRepository extends JpaRepository<Destination, UUID> {

}