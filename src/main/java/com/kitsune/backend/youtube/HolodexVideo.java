package com.kitsune.backend.youtube;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kitsune.backend.serde.ZonedDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@JsonInclude
public class HolodexVideo {
    String type;

    @JsonProperty("available_at")
    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
    LocalDateTime availableAt;

    @JsonProperty("start_scheduled")
    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
    LocalDateTime startScheduled;

    @JsonProperty("start_actual")
    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
    LocalDateTime startActual;

    @JsonProperty("live_viewers")
    Long liveViewers;

    public LocalDateTime getScheduledTime() {
        if (startScheduled != null) {
            return startScheduled;
        }

        if (startActual != null) {
            return startActual;
        }

        return availableAt;
    }
}
