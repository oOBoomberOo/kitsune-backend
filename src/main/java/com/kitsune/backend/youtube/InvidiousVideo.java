package com.kitsune.backend.youtube;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Data
@Builder
@Jacksonized
@JsonInclude
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class InvidiousVideo {
    String type;
    String title;
    String videoId;
    @ToString.Exclude
    String description;
    Long viewCount;
    String author;

    boolean paid;
    boolean liveNow;
    boolean isUpcoming;
    boolean isPostLiveDvr;

    Long published;
    Long premiereTimestamp;

    public boolean isPremiere() {
        return premiereTimestamp != null;
    }

    public OffsetDateTime getPublished() {
        return LocalDateTime.ofEpochSecond(published, 0, ZoneOffset.UTC).atOffset(ZoneOffset.UTC);
    }

    public OffsetDateTime getPublishedDate() {
        if (premiereTimestamp != null) {
            return LocalDateTime.ofEpochSecond(premiereTimestamp, 0, ZoneOffset.UTC).atOffset(ZoneOffset.UTC);
        }

        return getPublished();
    }
}
