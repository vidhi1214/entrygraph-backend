package com.entrygraph.backend.dto.response;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import com.entrygraph.backend.enums.DestinationCategory;
import com.entrygraph.backend.enums.DestinationStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DestinationResponse {

    private UUID id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private DestinationCategory category;
    private String description;
    private DestinationStatus status;
    private Instant createdAt;
    private Set<TagResponse> tags;
}
