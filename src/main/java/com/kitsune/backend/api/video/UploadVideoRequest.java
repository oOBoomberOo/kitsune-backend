package com.kitsune.backend.api.video;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Data
@Builder
@Jacksonized
@Schema
public class UploadVideoRequest {
    @NotBlank
    String videoId;

    @Nullable
    LocalDateTime startAt;

    @Nullable
    LocalDateTime endAt;
}
