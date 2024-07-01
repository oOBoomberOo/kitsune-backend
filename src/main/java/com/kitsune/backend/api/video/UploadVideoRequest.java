package com.kitsune.backend.api.video;

import com.kitsune.backend.constant.PostgresTime;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.OffsetDateTime;

@Data
@Builder
@Jacksonized
@Schema
public class UploadVideoRequest {
    @Nullable
    @Builder.Default
    private OffsetDateTime startAt = null;

    @Nullable
    @Builder.Default
    private OffsetDateTime endAt = PostgresTime.MAX;
}
