package com.entrygraph.backend.entity;

import java.util.HashSet;
import java.util.Set;

import com.entrygraph.backend.common.BaseEntity;
import com.entrygraph.backend.enums.DestinationCategory;
import com.entrygraph.backend.enums.DestinationStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "destinations")
public class Destination extends BaseEntity {

    private String name;

    private String address;

    private Double latitude;

    private Double longitude;

    @Enumerated(EnumType.STRING)
    private DestinationCategory category;

    private String description;

    @Enumerated(EnumType.STRING)
    private DestinationStatus status;

    @ManyToMany
    @JoinTable(
            name = "destination_tags",
            joinColumns = @JoinColumn(name = "destination_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();
}
