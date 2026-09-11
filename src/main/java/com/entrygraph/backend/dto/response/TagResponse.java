package com.entrygraph.backend.dto.response;

import java.time.Instant;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagResponse {

    private UUID id;
    private String name;
    private Instant createdAt;
}
