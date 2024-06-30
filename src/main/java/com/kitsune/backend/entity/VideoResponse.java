package com.kitsune.backend.entity;

import com.kitsune.backend.model.VideoStatus;
import com.kitsune.backend.model.VideoType;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Video}
 */
public record VideoResponse(@NotNull String id, @NotNull String title, @NotNull LocalDateTime addedAt,
                            @NotNull VideoType type,
                            @NotNull VideoStatus status, @NotNull LocalDateTime startAt, @NotNull LocalDateTime endAt,
                            String panicMessage) implements Serializable {
    public VideoResponse(Video video) {
        this(video.getId(), video.getTitle(), video.getAddedAt(), video.getType(), video.getStatus(), video.getStartAt(),
                video.getEndAt(), video.getPanicMessage());
    }
}