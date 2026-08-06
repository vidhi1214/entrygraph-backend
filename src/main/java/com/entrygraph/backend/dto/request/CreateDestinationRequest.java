package com.entrygraph.backend.dto.request;

import com.entrygraph.backend.enums.DestinationCategory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDestinationRequest {

    @NotBlank(message = "Destination name is required")
    @Size(max = 100, message = "Destination name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address cannot exceed 255 characters")
    private String address;

    @NotNull(message = "Latitude is required")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    private Double longitude;

    @NotNull(message = "Category is required")
    private DestinationCategory category;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
}