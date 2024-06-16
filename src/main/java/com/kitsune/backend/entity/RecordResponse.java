package com.kitsune.backend.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link Record}
 */
public record RecordResponse(UUID id, Long views, LocalDateTime createdAt) implements Serializable {

    public RecordResponse(Record record) {
        this(record.getId(), record.getViews(), record.getCreatedAt());
    }

}