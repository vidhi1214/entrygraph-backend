package com.entrygraph.backend.dto.request;

import com.entrygraph.backend.enums.DestinationCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDestinationRequest {

    private String name;

    private String address;

    private Double latitude;

    private Double longitude;

    private DestinationCategory category;

    private String description;
}