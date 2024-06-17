package com.kitsune.backend.serde;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctx) throws IOException, JacksonException {
        String content = p.getValueAsString();
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(content);
        return zonedDateTime
                .withZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
