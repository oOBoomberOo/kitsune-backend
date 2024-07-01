package com.kitsune.backend.youtube;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@JsonInclude
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class HolodexVideo {
    String type;

    @JsonProperty("available_at")
    OffsetDateTime availableAt;

    @JsonProperty("start_scheduled")
    OffsetDateTime startScheduled;

    @JsonProperty("start_actual")
    OffsetDateTime startActual;

    @JsonProperty("live_viewers")
    Long liveViewers;

    public OffsetDateTime getScheduledTime() {
        if (startScheduled != null) {
            return startScheduled;
        }

        if (startActual != null) {
            return startActual;
        }

        return availableAt;
    }
}
