package com.kitsune.backend.api.video;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kitsune.backend.model.VideoType;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@JsonInclude
public class VideoInfo {
    @NotNull
    String videoId;
    @NotNull
    LocalDateTime uploadDate;
    @NotNull
    LocalDateTime publishDate;
    @NotNull
    VideoType type;
    @NotNull
    String author;
    @NotNull
    String title;

    @ToString.Exclude
    String description;

    @NotNull
    long views;
}
