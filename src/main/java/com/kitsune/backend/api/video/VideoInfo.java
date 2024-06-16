package com.kitsune.backend.api.video;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kitsune.backend.model.VideoType;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Data
@Builder
@Jacksonized
@JsonInclude
public class VideoInfo {
    LocalDateTime uploadDate;
    LocalDateTime publishDate;
    VideoType type;
    String author;
    String title;

    @ToString.Exclude
    String description;

    long views;
}
