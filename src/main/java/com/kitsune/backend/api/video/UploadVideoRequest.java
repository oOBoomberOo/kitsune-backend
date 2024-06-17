package com.kitsune.backend.api.video;

import com.kitsune.backend.constant.PostgresTime;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Data
@Builder
@Jacksonized
@Schema
public class UploadVideoRequest {
    @Builder.Default
    LocalDateTime startAt = null;

    @NotNull
    @Builder.Default
    LocalDateTime endAt = PostgresTime.MAX;
}
