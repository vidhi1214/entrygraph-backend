package com.entrygraph.backend.common;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    private UUID id;

}