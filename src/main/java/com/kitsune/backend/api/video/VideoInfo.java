package com.kitsune.backend.api.video;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kitsune.backend.model.VideoType;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@JsonInclude
public class VideoInfo {
    @NotNull
    private String videoId;
    @NotNull
    private OffsetDateTime uploadDate;
    @NotNull
    private OffsetDateTime publishDate;
    @NotNull
    private VideoType type;
    @NotNull
    private String author;
    @NotNull
    private String title;

    @ToString.Exclude
    private String description;

    @NotNull
    private long views;
}
