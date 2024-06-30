package com.kitsune.backend.entity;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link Record}
 */
public record RecordResponse(@NotNull UUID id, @NotNull Long views,
                             @NotNull LocalDateTime createdAt) implements Serializable {

    public RecordResponse(Record record) {
        this(record.getId(), record.getViews(), record.getCreatedAt());
    }

}