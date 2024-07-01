package com.kitsune.backend.entity;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * DTO for {@link Record}
 */
public record RecordResponse(@NotNull UUID id, @NotNull Long views,
                             @NotNull OffsetDateTime createdAt) implements Serializable {

    public RecordResponse(Record record) {
        this(record.getId(), record.getViews(), record.getCreatedAt());
    }

}